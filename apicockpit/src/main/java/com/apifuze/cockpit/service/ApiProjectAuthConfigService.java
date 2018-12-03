package com.apifuze.cockpit.service;

import com.apifuze.cockpit.domain.ApiProjectAuthConfig;
import com.apifuze.cockpit.repository.ApiProjectAuthConfigRepository;
import com.apifuze.cockpit.service.dto.ApiProjectAuthConfigDTO;
import com.apifuze.cockpit.service.mapper.ApiProjectAuthConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing ApiProjectAuthConfig.
 */
@Service
@Transactional
public class ApiProjectAuthConfigService {

    private final Logger log = LoggerFactory.getLogger(ApiProjectAuthConfigService.class);

    private final ApiProjectAuthConfigRepository apiProjectAuthConfigRepository;

    private final ApiProjectAuthConfigMapper apiProjectAuthConfigMapper;

    public ApiProjectAuthConfigService(ApiProjectAuthConfigRepository apiProjectAuthConfigRepository, ApiProjectAuthConfigMapper apiProjectAuthConfigMapper) {
        this.apiProjectAuthConfigRepository = apiProjectAuthConfigRepository;
        this.apiProjectAuthConfigMapper = apiProjectAuthConfigMapper;
    }

    /**
     * Save a apiProjectAuthConfig.
     *
     * @param apiProjectAuthConfigDTO the entity to save
     * @return the persisted entity
     */
    public ApiProjectAuthConfigDTO save(ApiProjectAuthConfigDTO apiProjectAuthConfigDTO) {
        log.debug("Request to save ApiProjectAuthConfig : {}", apiProjectAuthConfigDTO);

        ApiProjectAuthConfig apiProjectAuthConfig = apiProjectAuthConfigMapper.toEntity(apiProjectAuthConfigDTO);
        apiProjectAuthConfig = apiProjectAuthConfigRepository.save(apiProjectAuthConfig);
        return apiProjectAuthConfigMapper.toDto(apiProjectAuthConfig);
    }

    /**
     * Get all the apiProjectAuthConfigs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ApiProjectAuthConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApiProjectAuthConfigs");
        return apiProjectAuthConfigRepository.findAll(pageable)
            .map(apiProjectAuthConfigMapper::toDto);
    }



    /**
     *  get all the apiProjectAuthConfigs where Project is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ApiProjectAuthConfigDTO> findAllWhereProjectIsNull() {
        log.debug("Request to get all apiProjectAuthConfigs where Project is null");
        return StreamSupport
            .stream(apiProjectAuthConfigRepository.findAll().spliterator(), false)
            .filter(apiProjectAuthConfig -> apiProjectAuthConfig.getProject() == null)
            .map(apiProjectAuthConfigMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one apiProjectAuthConfig by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ApiProjectAuthConfigDTO> findOne(Long id) {
        log.debug("Request to get ApiProjectAuthConfig : {}", id);
        return apiProjectAuthConfigRepository.findById(id)
            .map(apiProjectAuthConfigMapper::toDto);
    }

    /**
     * Delete the apiProjectAuthConfig by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ApiProjectAuthConfig : {}", id);
        apiProjectAuthConfigRepository.deleteById(id);
    }
}
