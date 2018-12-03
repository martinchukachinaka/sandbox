package com.apifuze.cockpit.initializer.service;

import com.apifuze.cockpit.domain.*;
import com.apifuze.cockpit.initializer.dto.ProjectDTO;
import com.apifuze.cockpit.initializer.dto.SystemInitDTO;
import com.apifuze.cockpit.repository.*;
import com.apifuze.cockpit.service.UserService;
import com.apifuze.cockpit.service.mapper.ApiPublisherProfileMapper;
import com.apifuze.cockpit.service.mapper.ApiServiceConfigMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Component

public class SamplePojectDataInitializer {

    private final Logger log = LoggerFactory.getLogger(SamplePojectDataInitializer.class);

    private SystemDataloadUtils systemDataloadUtils;

    private DataService dataService;

    private ApiServiceConfigRepository apiServiceConfigRepository;

    private ApiConsumerProfileRepository apiConsumerProfileRepository;

    private ApiProjectServiceRepository apiProjectServiceRepository;

    private ApiProjectRepository apiProjectRepository;

    private ApiProjectAuthConfigRepository apiProjectAuthConfigRepository;

    private ApiPublisherProfileRepository apiPublisherProfileRepository;

    private UserRepository userRepository;

    private UserService userService;

    private PlatformUserRepository platformUserRepository;


    private AtomicBoolean initialized = new AtomicBoolean(false);

    private Executor taskExecutor;


    public SamplePojectDataInitializer(DataService dataService, SystemDataloadUtils systemDataloadUtils, ApiServiceConfigRepository apiServiceConfigRepository,
                                       ApiConsumerProfileRepository apiConsumerProfileRepository,
                                       ApiProjectServiceRepository apiProjectServiceRepository,
                                       ApiProjectRepository apiProjectRepository,
                                       ApiProjectAuthConfigRepository apiProjectAuthConfigRepository,
                                       ApiPublisherProfileRepository apiPublisherProfileRepository,
                                       UserService userService,
                                       PlatformUserRepository platformUserRepository,
                                       Executor taskExecutor) {

        this.dataService = dataService;
        this.systemDataloadUtils = systemDataloadUtils;
        this.apiServiceConfigRepository = apiServiceConfigRepository;
        this.apiConsumerProfileRepository = apiConsumerProfileRepository;
        this.apiProjectServiceRepository = apiProjectServiceRepository;
        this.apiProjectRepository = apiProjectRepository;
        this.apiProjectAuthConfigRepository = apiProjectAuthConfigRepository;
        this.apiPublisherProfileRepository = apiPublisherProfileRepository;
        this.userService = userService;
        this.platformUserRepository = platformUserRepository;
        this.taskExecutor = taskExecutor;

    }


    @EventListener(ApplicationReadyEvent.class)
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void init() {
        taskExecutor.execute(() -> {
            while (!systemDataloadUtils.isInitialized()) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    log.error("Error waiting to load sample project data", e);
                }
                log.info("Waiting on system to finish initialization");
            }
            log.info("Loading sample project data");
            ProjectDTO projectDTO = dataService.getData("sample-project.json", ProjectDTO.class);
            if (projectDTO != null) {
                log.info("Initializing project [{}]", projectDTO.getName());
                List<ApiServiceConfig> requiredApis = apiServiceConfigRepository.findAllById(Arrays.asList(projectDTO.getRequiredApis()));

                ApiProject project = new ApiProject();
                project.setName(projectDTO.getName());
                project.setDescription(projectDTO.getDescription());
                Example<? extends ApiProject> example = Example.of(project);

                if (!apiProjectRepository.findOne(example).isPresent()) {

                    User user = userService.getUserWithAuthorities(4l).get();

                    if (!CollectionUtils.isEmpty(requiredApis)) {
                        Instant created = Instant.now();
                        ApiPublisherProfile publisher = apiPublisherProfileRepository.findAll().get(0);

                        PlatformUser platformUser = new PlatformUser();
                        platformUser.setPhoneNumber("08141376864qq");
                        platformUser.setUser(user);
                        platformUser = platformUserRepository.save(platformUser);

                        ApiConsumerProfile consumerProfile = new ApiConsumerProfile();
                        consumerProfile.setDateCreated(created);
                        consumerProfile.setActive(Boolean.TRUE);
                        consumerProfile.setOwner(publisher);
                        consumerProfile.setName(user.getLogin());
                        consumerProfile.setPlatformUser(platformUser);
                        consumerProfile = apiConsumerProfileRepository.save(consumerProfile);
                        platformUser.setApiConsumerProfile(consumerProfile);
                        platformUser = platformUserRepository.save(platformUser);

                        project = new ApiProject();
                        project.setName(projectDTO.getName());
                        project.setDescription(projectDTO.getDescription());
                        project.setDateCreated(created);
                        project.setActive(Boolean.TRUE);
                        List<ApiProjectService> apis = requiredApis.stream().map(s -> {
                            ApiProjectService apiProjectService = new ApiProjectService();
                            apiProjectService.setActive(Boolean.TRUE);
                            apiProjectService.setDateCreated(created);
                            apiProjectService.setServiceConfig(s);
                            apiProjectService.setName(s.getName());
                            return apiProjectService;
                        }).collect(Collectors.toList());
                        project.setApis(new HashSet<>(apiProjectServiceRepository.saveAll(apis)));
                        project.setOwner(consumerProfile);

                        project = apiProjectRepository.save(project);

                        ApiProjectAuthConfig apiKey = new ApiProjectAuthConfig();
                        apiKey.setDateCreated(created);
                        apiKey.setClientId("ClientId");
                        apiKey.setClientSecret("I am not even sure what this is");
                        apiKey.setProject(project);
                        apiKey.setActive(Boolean.TRUE);
                        apiKey = apiProjectAuthConfigRepository.save(apiKey);
                        project.setApiKey(apiKey);

                        project = apiProjectRepository.save(project);
                    }
                }

            }

        });

    }

}
