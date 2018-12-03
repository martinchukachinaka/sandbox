package com.apifuze.cockpit.service.mapper;

import com.apifuze.cockpit.domain.*;
import com.apifuze.cockpit.service.dto.ApiPublisherProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ApiPublisherProfile and its DTO ApiPublisherProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApiPublisherProfileMapper extends EntityMapper<ApiPublisherProfileDTO, ApiPublisherProfile> {


    @Mapping(target = "consumerProfiles", ignore = true)
    @Mapping(target = "serviceConfigs", ignore = true)
    @Mapping(target = "platformUser", ignore = true)
    ApiPublisherProfile toEntity(ApiPublisherProfileDTO apiPublisherProfileDTO);

    default ApiPublisherProfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApiPublisherProfile apiPublisherProfile = new ApiPublisherProfile();
        apiPublisherProfile.setId(id);
        return apiPublisherProfile;
    }
}
