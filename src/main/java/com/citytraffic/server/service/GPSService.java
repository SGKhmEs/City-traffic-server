package com.citytraffic.server.service;

import com.citytraffic.server.service.dto.GPSDTO;
import java.util.List;

/**
 * Service Interface for managing GPS.
 */
public interface GPSService {

    /**
     * Save a gPS.
     *
     * @param gPSDTO the entity to save
     * @return the persisted entity
     */
    GPSDTO save(GPSDTO gPSDTO);

    /**
     *  Get all the gPS.
     *
     *  @return the list of entities
     */
    List<GPSDTO> findAll();

    /**
     *  Get the "id" gPS.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    GPSDTO findOne(Long id);

    /**
     *  Delete the "id" gPS.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
