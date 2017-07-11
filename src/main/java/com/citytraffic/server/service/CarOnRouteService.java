package com.citytraffic.server.service;

import com.citytraffic.server.service.dto.CarOnRouteDTO;
import java.util.List;

/**
 * Service Interface for managing CarOnRoute.
 */
public interface CarOnRouteService {

    /**
     * Save a carOnRoute.
     *
     * @param carOnRouteDTO the entity to save
     * @return the persisted entity
     */
    CarOnRouteDTO save(CarOnRouteDTO carOnRouteDTO);

    /**
     *  Get all the carOnRoutes.
     *
     *  @return the list of entities
     */
    List<CarOnRouteDTO> findAll();

    /**
     *  Get the "id" carOnRoute.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CarOnRouteDTO findOne(Long id);

    /**
     *  Delete the "id" carOnRoute.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
