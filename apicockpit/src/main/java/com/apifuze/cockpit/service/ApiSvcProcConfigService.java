package com.apifuze.cockpit.service;

import com.apifuze.cockpit.domain.ApiSvcProcConfig;
import com.apifuze.cockpit.repository.ApiSvcProcConfigRepository;
import com.apifuze.cockpit.service.dto.ApiSvcProcConfigDTO;
import com.apifuze.cockpit.service.mapper.ApiSvcProcConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ApiSvcProcConfig.
 */
@Service
@Transactional
public class ApiSvcProcConfigService {

    private final Logger log = LoggerFactory.getLogger(ApiSvcProcConfigService.class);

    private final ApiSvcProcConfigRepository apiSvcProcConfigRepository;

    private final ApiSvcProcConfigMapper apiSvcProcConfigMapper;

    public ApiSvcProcConfigService(ApiSvcProcConfigRepository apiSvcProcConfigRepository, ApiSvcProcConfigMapper apiSvcProcConfigMapper) {
        this.apiSvcProcConfigRepository = apiSvcProcConfigRepository;
        this.apiSvcProcConfigMapper = apiSvcProcConfigMapper;
    }

    /**
     * Save a apiSvcProcConfig.
     *
     * @param apiSvcProcConfigDTO the entity to save
     * @return the persisted entity
     */
    public ApiSvcProcConfigDTO save(ApiSvcProcConfigDTO apiSvcProcConfigDTO) {
        log.debug("Request to save ApiSvcProcConfig : {}", apiSvcProcConfigDTO);

        ApiSvcProcConfig apiSvcProcConfig = apiSvcProcConfigMapper.toEntity(apiSvcProcConfigDTO);
        apiSvcProcConfig = apiSvcProcConfigRepository.save(apiSvcProcConfig);
        return apiSvcProcConfigMapper.toDto(apiSvcProcConfig);
    }

    /**
     * Get all the apiSvcProcConfigs.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ApiSvcProcConfigDTO> findAll() {
        log.debug("Request to get all ApiSvcProcConfigs");
        return apiSvcProcConfigRepository.findAll().stream()
            .map(apiSvcProcConfigMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one apiSvcProcConfig by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ApiSvcProcConfigDTO> findOne(Long id) {
        log.debug("Request to get ApiSvcProcConfig : {}", id);
        return apiSvcProcConfigRepository.findById(id)
            .map(apiSvcProcConfigMapper::toDto);
    }

    /**
     * Delete the apiSvcProcConfig by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ApiSvcProcConfig : {}", id);
        apiSvcProcConfigRepository.deleteById(id);
    }
}
