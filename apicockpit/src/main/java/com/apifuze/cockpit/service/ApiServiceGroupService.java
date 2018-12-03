package com.apifuze.cockpit.service;

import com.apifuze.cockpit.domain.ApiServiceGroup;
import com.apifuze.cockpit.repository.ApiServiceGroupRepository;
import com.apifuze.cockpit.service.dto.ApiServiceGroupDTO;
import com.apifuze.cockpit.service.mapper.ApiServiceGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ApiServiceGroup.
 */
@Service
@Transactional
public class ApiServiceGroupService {

    private final Logger log = LoggerFactory.getLogger(ApiServiceGroupService.class);

    private final ApiServiceGroupRepository apiServiceGroupRepository;

    private final ApiServiceGroupMapper apiServiceGroupMapper;

    public ApiServiceGroupService(ApiServiceGroupRepository apiServiceGroupRepository, ApiServiceGroupMapper apiServiceGroupMapper) {
        this.apiServiceGroupRepository = apiServiceGroupRepository;
        this.apiServiceGroupMapper = apiServiceGroupMapper;
    }

    /**
     * Save a apiServiceGroup.
     *
     * @param apiServiceGroupDTO the entity to save
     * @return the persisted entity
     */
    public ApiServiceGroupDTO save(ApiServiceGroupDTO apiServiceGroupDTO) {
        log.debug("Request to save ApiServiceGroup : {}", apiServiceGroupDTO);

        ApiServiceGroup apiServiceGroup = apiServiceGroupMapper.toEntity(apiServiceGroupDTO);
        apiServiceGroup = apiServiceGroupRepository.save(apiServiceGroup);
        return apiServiceGroupMapper.toDto(apiServiceGroup);
    }

    /**
     * Get all the apiServiceGroups.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ApiServiceGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApiServiceGroups");
        return apiServiceGroupRepository.findAll(pageable)
            .map(apiServiceGroupMapper::toDto);
    }


    /**
     * Get one apiServiceGroup by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ApiServiceGroupDTO> findOne(Long id) {
        log.debug("Request to get ApiServiceGroup : {}", id);
        return apiServiceGroupRepository.findById(id)
            .map(apiServiceGroupMapper::toDto);
    }

    /**
     * Delete the apiServiceGroup by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ApiServiceGroup : {}", id);
        apiServiceGroupRepository.deleteById(id);
    }
}
