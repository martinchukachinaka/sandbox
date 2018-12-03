package com.apifuze.cockpit.service.mapper;

import com.apifuze.cockpit.domain.*;
import com.apifuze.cockpit.service.dto.GrovyProcConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GrovyProcConfig and its DTO GrovyProcConfigDTO.
 */
@Mapper(componentModel = "spring", uses = {ApiSvcProcConfigMapper.class})
public interface GrovyProcConfigMapper extends EntityMapper<GrovyProcConfigDTO, GrovyProcConfig> {

    @Mapping(source = "config.id", target = "configId")
    @Mapping(source = "config.name", target = "configName")
    GrovyProcConfigDTO toDto(GrovyProcConfig grovyProcConfig);

    @Mapping(source = "configId", target = "config")
    GrovyProcConfig toEntity(GrovyProcConfigDTO grovyProcConfigDTO);

    default GrovyProcConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        GrovyProcConfig grovyProcConfig = new GrovyProcConfig();
        grovyProcConfig.setId(id);
        return grovyProcConfig;
    }
}
