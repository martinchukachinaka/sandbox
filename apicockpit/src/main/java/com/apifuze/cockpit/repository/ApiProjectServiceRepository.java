package com.apifuze.cockpit.repository;

import com.apifuze.cockpit.domain.ApiProjectService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApiProjectService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiProjectServiceRepository extends JpaRepository<ApiProjectService, Long> {

}
