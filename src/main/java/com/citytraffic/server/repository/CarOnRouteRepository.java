package com.citytraffic.server.repository;

import com.citytraffic.server.domain.CarOnRoute;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CarOnRoute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarOnRouteRepository extends JpaRepository<CarOnRoute,Long> {
    
}
