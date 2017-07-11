package com.citytraffic.server.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.citytraffic.server.service.BusRouteService;
import com.citytraffic.server.web.rest.util.HeaderUtil;
import com.citytraffic.server.service.dto.BusRouteDTO;
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
 * REST controller for managing BusRoute.
 */
@RestController
@RequestMapping("/api")
public class BusRouteResource {

    private final Logger log = LoggerFactory.getLogger(BusRouteResource.class);

    private static final String ENTITY_NAME = "busRoute";

    private final BusRouteService busRouteService;

    public BusRouteResource(BusRouteService busRouteService) {
        this.busRouteService = busRouteService;
    }

    /**
     * POST  /bus-routes : Create a new busRoute.
     *
     * @param busRouteDTO the busRouteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new busRouteDTO, or with status 400 (Bad Request) if the busRoute has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bus-routes")
    @Timed
    public ResponseEntity<BusRouteDTO> createBusRoute(@RequestBody BusRouteDTO busRouteDTO) throws URISyntaxException {
        log.debug("REST request to save BusRoute : {}", busRouteDTO);
        if (busRouteDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new busRoute cannot already have an ID")).body(null);
        }
        BusRouteDTO result = busRouteService.save(busRouteDTO);
        return ResponseEntity.created(new URI("/api/bus-routes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bus-routes : Updates an existing busRoute.
     *
     * @param busRouteDTO the busRouteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated busRouteDTO,
     * or with status 400 (Bad Request) if the busRouteDTO is not valid,
     * or with status 500 (Internal Server Error) if the busRouteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bus-routes")
    @Timed
    public ResponseEntity<BusRouteDTO> updateBusRoute(@RequestBody BusRouteDTO busRouteDTO) throws URISyntaxException {
        log.debug("REST request to update BusRoute : {}", busRouteDTO);
        if (busRouteDTO.getId() == null) {
            return createBusRoute(busRouteDTO);
        }
        BusRouteDTO result = busRouteService.save(busRouteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, busRouteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bus-routes : get all the busRoutes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of busRoutes in body
     */
    @GetMapping("/bus-routes")
    @Timed
    public List<BusRouteDTO> getAllBusRoutes() {
        log.debug("REST request to get all BusRoutes");
        return busRouteService.findAll();
    }

    /**
     * GET  /bus-routes/:id : get the "id" busRoute.
     *
     * @param id the id of the busRouteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the busRouteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bus-routes/{id}")
    @Timed
    public ResponseEntity<BusRouteDTO> getBusRoute(@PathVariable Long id) {
        log.debug("REST request to get BusRoute : {}", id);
        BusRouteDTO busRouteDTO = busRouteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(busRouteDTO));
    }

    /**
     * DELETE  /bus-routes/:id : delete the "id" busRoute.
     *
     * @param id the id of the busRouteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bus-routes/{id}")
    @Timed
    public ResponseEntity<Void> deleteBusRoute(@PathVariable Long id) {
        log.debug("REST request to delete BusRoute : {}", id);
        busRouteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
