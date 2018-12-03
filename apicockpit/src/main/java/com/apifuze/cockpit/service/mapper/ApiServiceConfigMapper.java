package com.apifuze.cockpit.service.mapper;

import com.apifuze.cockpit.domain.*;
import com.apifuze.cockpit.service.dto.ApiServiceConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ApiServiceConfig and its DTO ApiServiceConfigDTO.
 */
@Mapper(componentModel = "spring", uses = {ApiServiceGroupMapper.class})
public interface ApiServiceConfigMapper extends EntityMapper<ApiServiceConfigDTO, ApiServiceConfig> {

    @Mapping(source = "serviceGroup.id", target = "serviceGroupId")
    @Mapping(source = "serviceGroup.name", target = "serviceGroupName")
    ApiServiceConfigDTO toDto(ApiServiceConfig apiServiceConfig);

    @Mapping(source = "serviceGroupId", target = "serviceGroup")
    @Mapping(target = "actions", ignore = true)
    ApiServiceConfig toEntity(ApiServiceConfigDTO apiServiceConfigDTO);

    default ApiServiceConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApiServiceConfig apiServiceConfig = new ApiServiceConfig();
        apiServiceConfig.setId(id);
        return apiServiceConfig;
    }
}
