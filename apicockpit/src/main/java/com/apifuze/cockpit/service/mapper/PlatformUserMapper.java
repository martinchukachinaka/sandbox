package com.apifuze.cockpit.service.mapper;

import com.apifuze.cockpit.domain.*;
import com.apifuze.cockpit.service.dto.PlatformUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PlatformUser and its DTO PlatformUserDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ApiPublisherProfileMapper.class, ApiConsumerProfileMapper.class})
public interface PlatformUserMapper extends EntityMapper<PlatformUserDTO, PlatformUser> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "apiPublisherProfile.id", target = "apiPublisherProfileId")
    @Mapping(source = "apiConsumerProfile.id", target = "apiConsumerProfileId")
    PlatformUserDTO toDto(PlatformUser platformUser);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "apiPublisherProfileId", target = "apiPublisherProfile")
    @Mapping(source = "apiConsumerProfileId", target = "apiConsumerProfile")
    PlatformUser toEntity(PlatformUserDTO platformUserDTO);

    default PlatformUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlatformUser platformUser = new PlatformUser();
        platformUser.setId(id);
        return platformUser;
    }
}
