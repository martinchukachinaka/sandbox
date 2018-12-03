package com.apifuze.cockpit.service.mapper;

import com.apifuze.cockpit.domain.*;
import com.apifuze.cockpit.service.dto.ApiServiceGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ApiServiceGroup and its DTO ApiServiceGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {ApiPublisherProfileMapper.class})
public interface ApiServiceGroupMapper extends EntityMapper<ApiServiceGroupDTO, ApiServiceGroup> {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.name", target = "ownerName")
    ApiServiceGroupDTO toDto(ApiServiceGroup apiServiceGroup);

    @Mapping(target = "availableServices", ignore = true)
    @Mapping(source = "ownerId", target = "owner")
    ApiServiceGroup toEntity(ApiServiceGroupDTO apiServiceGroupDTO);

    default ApiServiceGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApiServiceGroup apiServiceGroup = new ApiServiceGroup();
        apiServiceGroup.setId(id);
        return apiServiceGroup;
    }
}
