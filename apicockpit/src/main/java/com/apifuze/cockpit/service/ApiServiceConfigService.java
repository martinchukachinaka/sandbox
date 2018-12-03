package com.apifuze.cockpit.service;

import com.apifuze.cockpit.domain.ApiServiceConfig;
import com.apifuze.cockpit.repository.ApiServiceConfigRepository;
import com.apifuze.cockpit.service.dto.ApiServiceConfigDTO;
import com.apifuze.cockpit.service.mapper.ApiServiceConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ApiServiceConfig.
 */
@Service
@Transactional
public class ApiServiceConfigService {

    private final Logger log = LoggerFactory.getLogger(ApiServiceConfigService.class);

    private final ApiServiceConfigRepository apiServiceConfigRepository;

    private final ApiServiceConfigMapper apiServiceConfigMapper;

    public ApiServiceConfigService(ApiServiceConfigRepository apiServiceConfigRepository, ApiServiceConfigMapper apiServiceConfigMapper) {
        this.apiServiceConfigRepository = apiServiceConfigRepository;
        this.apiServiceConfigMapper = apiServiceConfigMapper;
    }

    /**
     * Save a apiServiceConfig.
     *
     * @param apiServiceConfigDTO the entity to save
     * @return the persisted entity
     */
    public ApiServiceConfigDTO save(ApiServiceConfigDTO apiServiceConfigDTO) {
        log.debug("Request to save ApiServiceConfig : {}", apiServiceConfigDTO);

        ApiServiceConfig apiServiceConfig = apiServiceConfigMapper.toEntity(apiServiceConfigDTO);
        apiServiceConfig = apiServiceConfigRepository.save(apiServiceConfig);
        return apiServiceConfigMapper.toDto(apiServiceConfig);
    }

    /**
     * Get all the apiServiceConfigs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ApiServiceConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApiServiceConfigs");
        return apiServiceConfigRepository.findAll(pageable)
            .map(apiServiceConfigMapper::toDto);
    }


    /**
     * Get one apiServiceConfig by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ApiServiceConfigDTO> findOne(Long id) {
        log.debug("Request to get ApiServiceConfig : {}", id);
        return apiServiceConfigRepository.findById(id)
            .map(apiServiceConfigMapper::toDto);
    }

    /**
     * Delete the apiServiceConfig by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ApiServiceConfig : {}", id);
        apiServiceConfigRepository.deleteById(id);
    }
}
