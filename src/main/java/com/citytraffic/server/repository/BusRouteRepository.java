package com.citytraffic.server.repository;

import com.citytraffic.server.domain.BusRoute;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BusRoute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusRouteRepository extends JpaRepository<BusRoute,Long> {
    
}
