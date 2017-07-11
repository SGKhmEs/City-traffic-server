package com.citytraffic.server.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.citytraffic.server.service.CarOnRouteService;
import com.citytraffic.server.web.rest.util.HeaderUtil;
import com.citytraffic.server.service.dto.CarOnRouteDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CarOnRoute.
 */
@RestController
@RequestMapping("/api")
public class CarOnRouteResource {

    private final Logger log = LoggerFactory.getLogger(CarOnRouteResource.class);

    private static final String ENTITY_NAME = "carOnRoute";

    private final CarOnRouteService carOnRouteService;

    public CarOnRouteResource(CarOnRouteService carOnRouteService) {
        this.carOnRouteService = carOnRouteService;
    }

    /**
     * POST  /car-on-routes : Create a new carOnRoute.
     *
     * @param carOnRouteDTO the carOnRouteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carOnRouteDTO, or with status 400 (Bad Request) if the carOnRoute has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/car-on-routes")
    @Timed
    public ResponseEntity<CarOnRouteDTO> createCarOnRoute(@RequestBody CarOnRouteDTO carOnRouteDTO) throws URISyntaxException {
        log.debug("REST request to save CarOnRoute : {}", carOnRouteDTO);
        if (carOnRouteDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new carOnRoute cannot already have an ID")).body(null);
        }
        CarOnRouteDTO result = carOnRouteService.save(carOnRouteDTO);
        return ResponseEntity.created(new URI("/api/car-on-routes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /car-on-routes : Updates an existing carOnRoute.
     *
     * @param carOnRouteDTO the carOnRouteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carOnRouteDTO,
     * or with status 400 (Bad Request) if the carOnRouteDTO is not valid,
     * or with status 500 (Internal Server Error) if the carOnRouteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/car-on-routes")
    @Timed
    public ResponseEntity<CarOnRouteDTO> updateCarOnRoute(@RequestBody CarOnRouteDTO carOnRouteDTO) throws URISyntaxException {
        log.debug("REST request to update CarOnRoute : {}", carOnRouteDTO);
        if (carOnRouteDTO.getId() == null) {
            return createCarOnRoute(carOnRouteDTO);
        }
        CarOnRouteDTO result = carOnRouteService.save(carOnRouteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carOnRouteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /car-on-routes : get all the carOnRoutes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of carOnRoutes in body
     */
    @GetMapping("/car-on-routes")
    @Timed
    public List<CarOnRouteDTO> getAllCarOnRoutes() {
        log.debug("REST request to get all CarOnRoutes");
        return carOnRouteService.findAll();
    }

    /**
     * GET  /car-on-routes/:id : get the "id" carOnRoute.
     *
     * @param id the id of the carOnRouteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carOnRouteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/car-on-routes/{id}")
    @Timed
    public ResponseEntity<CarOnRouteDTO> getCarOnRoute(@PathVariable Long id) {
        log.debug("REST request to get CarOnRoute : {}", id);
        CarOnRouteDTO carOnRouteDTO = carOnRouteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(carOnRouteDTO));
    }

    /**
     * DELETE  /car-on-routes/:id : delete the "id" carOnRoute.
     *
     * @param id the id of the carOnRouteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/car-on-routes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCarOnRoute(@PathVariable Long id) {
        log.debug("REST request to delete CarOnRoute : {}", id);
        carOnRouteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
