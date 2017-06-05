package com.citytraffic.server.repository;

import com.citytraffic.server.domain.CabPosition;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CabPosition entity.
 */
@SuppressWarnings("unused")
public interface CabPositionRepository extends JpaRepository<CabPosition,Long> {

}
