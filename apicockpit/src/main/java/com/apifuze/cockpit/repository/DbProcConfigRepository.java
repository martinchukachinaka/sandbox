package com.apifuze.cockpit.repository;

import com.apifuze.cockpit.domain.DbProcConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DbProcConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DbProcConfigRepository extends JpaRepository<DbProcConfig, Long> {

}
