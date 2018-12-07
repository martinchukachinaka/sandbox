package com.apifuze.cockpit.repository;

import com.apifuze.cockpit.domain.ApiConsumerProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApiConsumerProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiConsumerProfileRepository extends JpaRepository<ApiConsumerProfile, Long> {

    ApiConsumerProfile findByPlatformUserId(Long id);

    ApiConsumerProfile findByPlatformUserUserId(Long id);
}
