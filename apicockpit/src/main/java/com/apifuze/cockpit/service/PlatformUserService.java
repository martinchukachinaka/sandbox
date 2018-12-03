package com.apifuze.cockpit.service;

import com.apifuze.cockpit.domain.PlatformUser;
import com.apifuze.cockpit.repository.PlatformUserRepository;
import com.apifuze.cockpit.service.dto.PlatformUserDTO;
import com.apifuze.cockpit.service.mapper.PlatformUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PlatformUser.
 */
@Service
@Transactional
public class PlatformUserService {

    private final Logger log = LoggerFactory.getLogger(PlatformUserService.class);

    private final PlatformUserRepository platformUserRepository;

    private final PlatformUserMapper platformUserMapper;

    public PlatformUserService(PlatformUserRepository platformUserRepository, PlatformUserMapper platformUserMapper) {
        this.platformUserRepository = platformUserRepository;
        this.platformUserMapper = platformUserMapper;
    }

    /**
     * Save a platformUser.
     *
     * @param platformUserDTO the entity to save
     * @return the persisted entity
     */
    public PlatformUserDTO save(PlatformUserDTO platformUserDTO) {
        log.debug("Request to save PlatformUser : {}", platformUserDTO);

        PlatformUser platformUser = platformUserMapper.toEntity(platformUserDTO);
        platformUser = platformUserRepository.save(platformUser);
        return platformUserMapper.toDto(platformUser);
    }

    /**
     * Get all the platformUsers.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PlatformUserDTO> findAll() {
        log.debug("Request to get all PlatformUsers");
        return platformUserRepository.findAll().stream()
            .map(platformUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one platformUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PlatformUserDTO> findOne(Long id) {
        log.debug("Request to get PlatformUser : {}", id);
        return platformUserRepository.findById(id)
            .map(platformUserMapper::toDto);
    }

    /**
     * Delete the platformUser by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PlatformUser : {}", id);
        platformUserRepository.deleteById(id);
    }
}
