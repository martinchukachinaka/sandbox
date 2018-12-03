package com.apifuze.cockpit.service.mapper;

import com.apifuze.cockpit.domain.*;
import com.apifuze.cockpit.service.dto.ApiProjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ApiProject and its DTO ApiProjectDTO.
 */
@Mapper(componentModel = "spring", uses = {ApiProjectAuthConfigMapper.class, ApiProjectServiceMapper.class, ApiConsumerProfileMapper.class})
public interface ApiProjectMapper extends EntityMapper<ApiProjectDTO, ApiProject> {

    @Mapping(source = "apiKey.id", target = "apiKeyId")
    @Mapping(source = "apiKey.clientId", target = "apiKeyClientId")
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.name", target = "ownerName")
    ApiProjectDTO toDto(ApiProject apiProject);

    @Mapping(source = "apiKeyId", target = "apiKey")
    @Mapping(source = "ownerId", target = "owner")
    ApiProject toEntity(ApiProjectDTO apiProjectDTO);

    default ApiProject fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApiProject apiProject = new ApiProject();
        apiProject.setId(id);
        return apiProject;
    }
}
