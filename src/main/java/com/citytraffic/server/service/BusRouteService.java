package com.citytraffic.server.service;

import com.citytraffic.server.service.dto.BusRouteDTO;
import java.util.List;

/**
 * Service Interface for managing BusRoute.
 */
public interface BusRouteService {

    /**
     * Save a busRoute.
     *
     * @param busRouteDTO the entity to save
     * @return the persisted entity
     */
    BusRouteDTO save(BusRouteDTO busRouteDTO);

    /**
     *  Get all the busRoutes.
     *
     *  @return the list of entities
     */
    List<BusRouteDTO> findAll();

    /**
     *  Get the "id" busRoute.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BusRouteDTO findOne(Long id);

    /**
     *  Delete the "id" busRoute.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
