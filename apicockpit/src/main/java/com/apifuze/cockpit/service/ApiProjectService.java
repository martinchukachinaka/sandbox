package com.apifuze.cockpit.service;

import com.apifuze.cockpit.domain.ApiProject;
import com.apifuze.cockpit.domain.ApiProjectAuthConfig;
import com.apifuze.cockpit.repository.ApiProjectAuthConfigRepository;
import com.apifuze.cockpit.repository.ApiProjectRepository;
import com.apifuze.cockpit.service.dto.ApiProjectDTO;
import com.apifuze.cockpit.service.mapper.ApiProjectMapper;
import com.apifuze.cockpit.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing ApiProject.
 */
@Service
@Transactional
public class ApiProjectService {

    private final Logger log = LoggerFactory.getLogger(ApiProjectService.class);

    private final ApiProjectRepository apiProjectRepository;

    private final ApiProjectAuthConfigRepository apiProjectAuthConfigRepository;

    private final ApiProjectMapper apiProjectMapper;

    public ApiProjectService(ApiProjectRepository apiProjectRepository, ApiProjectAuthConfigRepository apiProjectAuthConfigRepository, ApiProjectMapper apiProjectMapper) {
        this.apiProjectRepository = apiProjectRepository;
        this.apiProjectAuthConfigRepository=apiProjectAuthConfigRepository;
        this.apiProjectMapper = apiProjectMapper;
    }

    /**
     * Save a apiProject.
     *
     * @param apiProjectDTO the entity to save
     * @return the persisted entity
     */
    public ApiProjectDTO save(ApiProjectDTO apiProjectDTO) {
        log.debug("Request to save ApiProject : {}", apiProjectDTO);
        ApiProjectAuthConfig apiKey=new ApiProjectAuthConfig();
        apiKey.setActive(Boolean.TRUE);
        apiKey.setDateCreated(Instant.now());
        apiKey.setClientSecret(RandomUtil.generatePassword());
        apiKey.setClientId(RandomUtil.generateActivationKey());
        apiKey=apiProjectAuthConfigRepository.save(apiKey);
        apiProjectDTO.setApiKeyId(apiKey.getId());
        if(apiProjectDTO.getDateCreated()==null) {
            apiProjectDTO.setDateCreated(Instant.now());
        }
        ApiProject apiProject = apiProjectMapper.toEntity(apiProjectDTO);
        apiProject = apiProjectRepository.save(apiProject);
        return apiProjectMapper.toDto(apiProject);
    }

    /**
     * Get all the apiProjects.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ApiProjectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApiProjects");
        return apiProjectRepository.findAll(pageable)
            .map(apiProjectMapper::toDto);
    }

    /**
     * Get all the ApiProject with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ApiProjectDTO> findAllWithEagerRelationships(Pageable pageable) {
        return apiProjectRepository.findAllWithEagerRelationships(pageable).map(apiProjectMapper::toDto);
    }
    

    /**
     * Get one apiProject by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ApiProjectDTO> findOne(Long id) {
        log.debug("Request to get ApiProject : {}", id);
        return apiProjectRepository.findOneWithEagerRelationships(id)
            .map(apiProjectMapper::toDto);
    }

    /**
     * Delete the apiProject by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ApiProject : {}", id);
        apiProjectRepository.deleteById(id);
    }
}
