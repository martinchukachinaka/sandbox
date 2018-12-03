package com.apifuze.cockpit.service;

import com.apifuze.cockpit.domain.ApiConsumerProfile;
import com.apifuze.cockpit.repository.ApiConsumerProfileRepository;
import com.apifuze.cockpit.service.dto.ApiConsumerProfileDTO;
import com.apifuze.cockpit.service.mapper.ApiConsumerProfileMapper;
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
 * Service Implementation for managing ApiConsumerProfile.
 */
@Service
@Transactional
public class ApiConsumerProfileService {

    private final Logger log = LoggerFactory.getLogger(ApiConsumerProfileService.class);

    private final ApiConsumerProfileRepository apiConsumerProfileRepository;

    private final ApiConsumerProfileMapper apiConsumerProfileMapper;

    public ApiConsumerProfileService(ApiConsumerProfileRepository apiConsumerProfileRepository, ApiConsumerProfileMapper apiConsumerProfileMapper) {
        this.apiConsumerProfileRepository = apiConsumerProfileRepository;
        this.apiConsumerProfileMapper = apiConsumerProfileMapper;
    }

    /**
     * Save a apiConsumerProfile.
     *
     * @param apiConsumerProfileDTO the entity to save
     * @return the persisted entity
     */
    public ApiConsumerProfileDTO save(ApiConsumerProfileDTO apiConsumerProfileDTO) {
        log.debug("Request to save ApiConsumerProfile : {}", apiConsumerProfileDTO);

        ApiConsumerProfile apiConsumerProfile = apiConsumerProfileMapper.toEntity(apiConsumerProfileDTO);
        apiConsumerProfile = apiConsumerProfileRepository.save(apiConsumerProfile);
        return apiConsumerProfileMapper.toDto(apiConsumerProfile);
    }

    /**
     * Get all the apiConsumerProfiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ApiConsumerProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApiConsumerProfiles");
        return apiConsumerProfileRepository.findAll(pageable)
            .map(apiConsumerProfileMapper::toDto);
    }



    /**
     *  get all the apiConsumerProfiles where PlatformUser is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ApiConsumerProfileDTO> findAllWherePlatformUserIsNull() {
        log.debug("Request to get all apiConsumerProfiles where PlatformUser is null");
        return StreamSupport
            .stream(apiConsumerProfileRepository.findAll().spliterator(), false)
            .filter(apiConsumerProfile -> apiConsumerProfile.getPlatformUser() == null)
            .map(apiConsumerProfileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one apiConsumerProfile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ApiConsumerProfileDTO> findOne(Long id) {
        log.debug("Request to get ApiConsumerProfile : {}", id);
        return apiConsumerProfileRepository.findById(id)
            .map(apiConsumerProfileMapper::toDto);
    }

    /**
     * Delete the apiConsumerProfile by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ApiConsumerProfile : {}", id);
        apiConsumerProfileRepository.deleteById(id);
    }
}
