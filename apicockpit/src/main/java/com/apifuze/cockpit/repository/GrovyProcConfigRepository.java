package com.apifuze.cockpit.repository;

import com.apifuze.cockpit.domain.GrovyProcConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GrovyProcConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrovyProcConfigRepository extends JpaRepository<GrovyProcConfig, Long> {

}
