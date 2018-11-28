package com.apifuze.cockpit.service.mapper;

import com.apifuze.cockpit.domain.*;
import com.apifuze.cockpit.service.dto.ApiProjectAuthConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ApiProjectAuthConfig and its DTO ApiProjectAuthConfigDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApiProjectAuthConfigMapper extends EntityMapper<ApiProjectAuthConfigDTO, ApiProjectAuthConfig> {


    @Mapping(target = "project", ignore = true)
    ApiProjectAuthConfig toEntity(ApiProjectAuthConfigDTO apiProjectAuthConfigDTO);

    default ApiProjectAuthConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApiProjectAuthConfig apiProjectAuthConfig = new ApiProjectAuthConfig();
        apiProjectAuthConfig.setId(id);
        return apiProjectAuthConfig;
    }
}
