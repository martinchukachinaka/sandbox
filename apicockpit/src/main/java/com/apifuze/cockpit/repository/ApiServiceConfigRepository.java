package com.apifuze.cockpit.repository;

import com.apifuze.cockpit.domain.ApiServiceConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApiServiceConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiServiceConfigRepository extends JpaRepository<ApiServiceConfig, Long> {

}
