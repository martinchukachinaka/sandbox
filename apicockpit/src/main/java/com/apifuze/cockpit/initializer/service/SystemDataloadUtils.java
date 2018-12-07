package com.apifuze.cockpit.initializer.service;


import com.apifuze.cockpit.domain.*;
import com.apifuze.cockpit.initializer.dto.SystemInitDTO;
import com.apifuze.cockpit.repository.*;
import com.apifuze.cockpit.service.mapper.ApiPublisherProfileMapper;
import com.apifuze.cockpit.service.mapper.ApiServiceConfigMapper;
import com.apifuze.utils.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Component

public class SystemDataloadUtils {

    private final Logger log = LoggerFactory.getLogger(SystemDataloadUtils.class);

    private ApiPublisherProfileRepository apiPublisherProfileRepository;


    private ApiServiceConfigRepository apiServiceConfigRepository;

    private ApiServiceGroupRepository apiServiceGroupRepository;

    private PlatformUserRepository platformUserRepository;


    private final DataService dataService;


    private AtomicBoolean initialized = new AtomicBoolean(false);

    private ApiPublisherProfileMapper apiPublisherProfileMapper;

    private ApiServiceConfigMapper apiServiceConfigMapper;

    private UserRepository userRepository;

    public SystemDataloadUtils(UserRepository userRepository, ApiPublisherProfileRepository apiPublisherProfileRepository, ApiServiceGroupRepository apiServiceGroupRepository, ApiServiceConfigRepository apiServiceConfigRepository,
                               PlatformUserRepository platformUserRepository, DataService dataService, ApiServiceConfigMapper apiServiceConfigMapper,
                               ApiPublisherProfileMapper apiPublisherProfileMapper) {
        this.userRepository = userRepository;
        this.apiPublisherProfileRepository = apiPublisherProfileRepository;
        this.apiServiceConfigRepository = apiServiceConfigRepository;
        this.apiServiceGroupRepository = apiServiceGroupRepository;
        this.platformUserRepository = platformUserRepository;
        this.apiServiceConfigMapper = apiServiceConfigMapper;
        this.apiPublisherProfileMapper = apiPublisherProfileMapper;
        this.dataService = dataService;

    }



    public Boolean isInitialized() {
        return initialized.get();
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void init() {
        if (apiPublisherProfileRepository.count() == 0) {
            log.info("Initializing system");
            Instant timestamp = Instant.now();
            SystemInitDTO systemData = this.dataService.getData("system-init", SystemInitDTO.class);
            if (systemData != null) {
                User user = userRepository.getOne(3l);
                PlatformUser platformUser = new PlatformUser();
                platformUser.setPhoneNumber("08141376868");
                platformUser.setUser(user);

                platformUser = platformUserRepository.save(platformUser);

                ApiPublisherProfile publisherProfile = apiPublisherProfileMapper.toEntity(systemData.getPublisherInfo());
                publisherProfile.setDateCreated(timestamp);
                publisherProfile.setPlatformUser(platformUser);

                final ApiPublisherProfile savedPublisherProfile = apiPublisherProfileRepository.save(publisherProfile);
                platformUser.setApiPublisherProfile(savedPublisherProfile);
                platformUser = platformUserRepository.save(platformUser);

                final Map<String, ApiServiceGroup> groupMap = new HashMap();
                systemData.getServiceList().stream().forEach(g -> {
                    ApiServiceGroup group = new ApiServiceGroup();
                    group.setName(g.getServiceGroupName());
                    Example<? extends ApiServiceGroup> example = Example.of(group);
                    if (!apiServiceGroupRepository.findOne(example).isPresent()) {
                        group.setDescription(g.getServiceGroupName());
                        group.setOwner(savedPublisherProfile);
                        final ApiServiceGroup savedGroup = apiServiceGroupRepository.save(group);
                        groupMap.put(g.getServiceGroupName(), savedGroup);
                    }
                });

                Collection<? extends ApiServiceConfig> serviceConfigList = systemData.getServiceList().stream().map(e -> {
                    if (groupMap.containsKey(e.getServiceGroupName())) {
                        e.setDateCreated(timestamp);
                        ApiServiceConfig entity = apiServiceConfigMapper.toEntity(e);
                        entity.setServiceGroup(groupMap.get(e.getServiceGroupName()));
                        entity.serviceDocUrl(e.getServiceUrl());
                        entity.setCode(e.getName());
                        return entity;
                    }
                    return null;
                }).filter(e->e!=null) .collect(Collectors.toSet());
                apiServiceConfigRepository.saveAll(serviceConfigList);

            }
            initialized = new AtomicBoolean(true);
        }
        initialized = new AtomicBoolean(true);
    }
}
