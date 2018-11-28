package com.apifuze.cockpit.repository;

import com.apifuze.cockpit.domain.ApiSvcProcConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApiSvcProcConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiSvcProcConfigRepository extends JpaRepository<ApiSvcProcConfig, Long> {

}
