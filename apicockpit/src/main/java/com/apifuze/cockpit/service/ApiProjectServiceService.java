package com.apifuze.cockpit.service;

import com.apifuze.cockpit.domain.ApiProjectService;
import com.apifuze.cockpit.repository.ApiProjectServiceRepository;
import com.apifuze.cockpit.service.dto.ApiProjectServiceDTO;
import com.apifuze.cockpit.service.mapper.ApiProjectServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ApiProjectService.
 */
@Service
@Transactional
public class ApiProjectServiceService {

    private final Logger log = LoggerFactory.getLogger(ApiProjectServiceService.class);

    private final ApiProjectServiceRepository apiProjectServiceRepository;

    private final ApiProjectServiceMapper apiProjectServiceMapper;

    public ApiProjectServiceService(ApiProjectServiceRepository apiProjectServiceRepository, ApiProjectServiceMapper apiProjectServiceMapper) {
        this.apiProjectServiceRepository = apiProjectServiceRepository;
        this.apiProjectServiceMapper = apiProjectServiceMapper;
    }

    /**
     * Save a apiProjectService.
     *
     * @param apiProjectServiceDTO the entity to save
     * @return the persisted entity
     */
    public ApiProjectServiceDTO save(ApiProjectServiceDTO apiProjectServiceDTO) {
        log.debug("Request to save ApiProjectService : {}", apiProjectServiceDTO);

        ApiProjectService apiProjectService = apiProjectServiceMapper.toEntity(apiProjectServiceDTO);
        apiProjectService = apiProjectServiceRepository.save(apiProjectService);
        return apiProjectServiceMapper.toDto(apiProjectService);
    }

    /**
     * Get all the apiProjectServices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ApiProjectServiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApiProjectServices");
        return apiProjectServiceRepository.findAll(pageable)
            .map(apiProjectServiceMapper::toDto);
    }


    /**
     * Get one apiProjectService by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ApiProjectServiceDTO> findOne(Long id) {
        log.debug("Request to get ApiProjectService : {}", id);
        return apiProjectServiceRepository.findById(id)
            .map(apiProjectServiceMapper::toDto);
    }

    /**
     * Delete the apiProjectService by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ApiProjectService : {}", id);
        apiProjectServiceRepository.deleteById(id);
    }
}
