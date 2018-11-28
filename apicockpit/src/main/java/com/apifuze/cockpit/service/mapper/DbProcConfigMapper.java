package com.apifuze.cockpit.service.mapper;

import com.apifuze.cockpit.domain.*;
import com.apifuze.cockpit.service.dto.DbProcConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DbProcConfig and its DTO DbProcConfigDTO.
 */
@Mapper(componentModel = "spring", uses = {ApiSvcProcConfigMapper.class})
public interface DbProcConfigMapper extends EntityMapper<DbProcConfigDTO, DbProcConfig> {

    @Mapping(source = "config.id", target = "configId")
    @Mapping(source = "config.name", target = "configName")
    DbProcConfigDTO toDto(DbProcConfig dbProcConfig);

    @Mapping(source = "configId", target = "config")
    DbProcConfig toEntity(DbProcConfigDTO dbProcConfigDTO);

    default DbProcConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        DbProcConfig dbProcConfig = new DbProcConfig();
        dbProcConfig.setId(id);
        return dbProcConfig;
    }
}
