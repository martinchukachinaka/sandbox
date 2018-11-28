package com.apifuze.cockpit.service.mapper;

import com.apifuze.cockpit.domain.*;
import com.apifuze.cockpit.service.dto.ApiConsumerProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ApiConsumerProfile and its DTO ApiConsumerProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {ApiPublisherProfileMapper.class})
public interface ApiConsumerProfileMapper extends EntityMapper<ApiConsumerProfileDTO, ApiConsumerProfile> {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.name", target = "ownerName")
    ApiConsumerProfileDTO toDto(ApiConsumerProfile apiConsumerProfile);

    @Mapping(target = "projects", ignore = true)
    @Mapping(target = "platformUser", ignore = true)
    @Mapping(source = "ownerId", target = "owner")
    ApiConsumerProfile toEntity(ApiConsumerProfileDTO apiConsumerProfileDTO);

    default ApiConsumerProfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApiConsumerProfile apiConsumerProfile = new ApiConsumerProfile();
        apiConsumerProfile.setId(id);
        return apiConsumerProfile;
    }
}
