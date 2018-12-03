package com.apifuze.cockpit.repository;

import com.apifuze.cockpit.domain.ApiServiceGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApiServiceGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiServiceGroupRepository extends JpaRepository<ApiServiceGroup, Long> {

}
