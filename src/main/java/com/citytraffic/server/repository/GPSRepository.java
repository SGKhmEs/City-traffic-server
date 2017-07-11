package com.citytraffic.server.repository;

import com.citytraffic.server.domain.GPS;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GPS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GPSRepository extends JpaRepository<GPS,Long> {
    
}
