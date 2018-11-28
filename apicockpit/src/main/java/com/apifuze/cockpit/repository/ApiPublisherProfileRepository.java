package com.apifuze.cockpit.repository;

import com.apifuze.cockpit.domain.ApiPublisherProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApiPublisherProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiPublisherProfileRepository extends JpaRepository<ApiPublisherProfile, Long> {

}
