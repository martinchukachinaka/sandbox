package com.apifuze.cockpit.service.mapper;

import com.apifuze.cockpit.domain.*;
import com.apifuze.cockpit.service.dto.ApiSvcProcConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ApiSvcProcConfig and its DTO ApiSvcProcConfigDTO.
 */
@Mapper(componentModel = "spring", uses = {ApiServiceConfigMapper.class})
public interface ApiSvcProcConfigMapper extends EntityMapper<ApiSvcProcConfigDTO, ApiSvcProcConfig> {

    @Mapping(source = "serviceConfig.id", target = "serviceConfigId")
    @Mapping(source = "serviceConfig.name", target = "serviceConfigName")
    ApiSvcProcConfigDTO toDto(ApiSvcProcConfig apiSvcProcConfig);

    @Mapping(source = "serviceConfigId", target = "serviceConfig")
    ApiSvcProcConfig toEntity(ApiSvcProcConfigDTO apiSvcProcConfigDTO);

    default ApiSvcProcConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApiSvcProcConfig apiSvcProcConfig = new ApiSvcProcConfig();
        apiSvcProcConfig.setId(id);
        return apiSvcProcConfig;
    }
}
