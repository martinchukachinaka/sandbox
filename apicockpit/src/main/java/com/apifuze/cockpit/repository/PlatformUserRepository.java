package com.apifuze.cockpit.repository;

import com.apifuze.cockpit.domain.PlatformUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PlatformUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlatformUserRepository extends JpaRepository<PlatformUser, Long> {

}
