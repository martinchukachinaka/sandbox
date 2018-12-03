package com.apifuze.cockpit.repository;

import com.apifuze.cockpit.domain.ApiProjectAuthConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApiProjectAuthConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiProjectAuthConfigRepository extends JpaRepository<ApiProjectAuthConfig, Long> {

}
