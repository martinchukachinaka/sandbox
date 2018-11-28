package com.apifuze.cockpit.service;

import com.apifuze.cockpit.domain.GrovyProcConfig;
import com.apifuze.cockpit.repository.GrovyProcConfigRepository;
import com.apifuze.cockpit.service.dto.GrovyProcConfigDTO;
import com.apifuze.cockpit.service.mapper.GrovyProcConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing GrovyProcConfig.
 */
@Service
@Transactional
public class GrovyProcConfigService {

    private final Logger log = LoggerFactory.getLogger(GrovyProcConfigService.class);

    private final GrovyProcConfigRepository grovyProcConfigRepository;

    private final GrovyProcConfigMapper grovyProcConfigMapper;

    public GrovyProcConfigService(GrovyProcConfigRepository grovyProcConfigRepository, GrovyProcConfigMapper grovyProcConfigMapper) {
        this.grovyProcConfigRepository = grovyProcConfigRepository;
        this.grovyProcConfigMapper = grovyProcConfigMapper;
    }

    /**
     * Save a grovyProcConfig.
     *
     * @param grovyProcConfigDTO the entity to save
     * @return the persisted entity
     */
    public GrovyProcConfigDTO save(GrovyProcConfigDTO grovyProcConfigDTO) {
        log.debug("Request to save GrovyProcConfig : {}", grovyProcConfigDTO);

        GrovyProcConfig grovyProcConfig = grovyProcConfigMapper.toEntity(grovyProcConfigDTO);
        grovyProcConfig = grovyProcConfigRepository.save(grovyProcConfig);
        return grovyProcConfigMapper.toDto(grovyProcConfig);
    }

    /**
     * Get all the grovyProcConfigs.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GrovyProcConfigDTO> findAll() {
        log.debug("Request to get all GrovyProcConfigs");
        return grovyProcConfigRepository.findAll().stream()
            .map(grovyProcConfigMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one grovyProcConfig by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<GrovyProcConfigDTO> findOne(Long id) {
        log.debug("Request to get GrovyProcConfig : {}", id);
        return grovyProcConfigRepository.findById(id)
            .map(grovyProcConfigMapper::toDto);
    }

    /**
     * Delete the grovyProcConfig by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GrovyProcConfig : {}", id);
        grovyProcConfigRepository.deleteById(id);
    }
}
