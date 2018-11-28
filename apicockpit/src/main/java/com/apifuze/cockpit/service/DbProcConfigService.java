package com.apifuze.cockpit.service;

import com.apifuze.cockpit.domain.DbProcConfig;
import com.apifuze.cockpit.repository.DbProcConfigRepository;
import com.apifuze.cockpit.service.dto.DbProcConfigDTO;
import com.apifuze.cockpit.service.mapper.DbProcConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing DbProcConfig.
 */
@Service
@Transactional
public class DbProcConfigService {

    private final Logger log = LoggerFactory.getLogger(DbProcConfigService.class);

    private final DbProcConfigRepository dbProcConfigRepository;

    private final DbProcConfigMapper dbProcConfigMapper;

    public DbProcConfigService(DbProcConfigRepository dbProcConfigRepository, DbProcConfigMapper dbProcConfigMapper) {
        this.dbProcConfigRepository = dbProcConfigRepository;
        this.dbProcConfigMapper = dbProcConfigMapper;
    }

    /**
     * Save a dbProcConfig.
     *
     * @param dbProcConfigDTO the entity to save
     * @return the persisted entity
     */
    public DbProcConfigDTO save(DbProcConfigDTO dbProcConfigDTO) {
        log.debug("Request to save DbProcConfig : {}", dbProcConfigDTO);

        DbProcConfig dbProcConfig = dbProcConfigMapper.toEntity(dbProcConfigDTO);
        dbProcConfig = dbProcConfigRepository.save(dbProcConfig);
        return dbProcConfigMapper.toDto(dbProcConfig);
    }

    /**
     * Get all the dbProcConfigs.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DbProcConfigDTO> findAll() {
        log.debug("Request to get all DbProcConfigs");
        return dbProcConfigRepository.findAll().stream()
            .map(dbProcConfigMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one dbProcConfig by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DbProcConfigDTO> findOne(Long id) {
        log.debug("Request to get DbProcConfig : {}", id);
        return dbProcConfigRepository.findById(id)
            .map(dbProcConfigMapper::toDto);
    }

    /**
     * Delete the dbProcConfig by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DbProcConfig : {}", id);
        dbProcConfigRepository.deleteById(id);
    }
}
