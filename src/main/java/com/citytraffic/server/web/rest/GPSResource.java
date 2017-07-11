package com.citytraffic.server.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.citytraffic.server.service.GPSService;
import com.citytraffic.server.web.rest.util.HeaderUtil;
import com.citytraffic.server.service.dto.GPSDTO;
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
 * REST controller for managing GPS.
 */
@RestController
@RequestMapping("/api")
public class GPSResource {

    private final Logger log = LoggerFactory.getLogger(GPSResource.class);

    private static final String ENTITY_NAME = "gPS";

    private final GPSService gPSService;

    public GPSResource(GPSService gPSService) {
        this.gPSService = gPSService;
    }

    /**
     * POST  /g-ps : Create a new gPS.
     *
     * @param gPSDTO the gPSDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gPSDTO, or with status 400 (Bad Request) if the gPS has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/g-ps")
    @Timed
    public ResponseEntity<GPSDTO> createGPS(@RequestBody GPSDTO gPSDTO) throws URISyntaxException {
        log.debug("REST request to save GPS : {}", gPSDTO);
        if (gPSDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new gPS cannot already have an ID")).body(null);
        }
        GPSDTO result = gPSService.save(gPSDTO);
        return ResponseEntity.created(new URI("/api/g-ps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /g-ps : Updates an existing gPS.
     *
     * @param gPSDTO the gPSDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gPSDTO,
     * or with status 400 (Bad Request) if the gPSDTO is not valid,
     * or with status 500 (Internal Server Error) if the gPSDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/g-ps")
    @Timed
    public ResponseEntity<GPSDTO> updateGPS(@RequestBody GPSDTO gPSDTO) throws URISyntaxException {
        log.debug("REST request to update GPS : {}", gPSDTO);
        if (gPSDTO.getId() == null) {
            return createGPS(gPSDTO);
        }
        GPSDTO result = gPSService.save(gPSDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gPSDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /g-ps : get all the gPS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gPS in body
     */
    @GetMapping("/g-ps")
    @Timed
    public List<GPSDTO> getAllGPS() {
        log.debug("REST request to get all GPS");
        return gPSService.findAll();
    }

    /**
     * GET  /g-ps/:id : get the "id" gPS.
     *
     * @param id the id of the gPSDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gPSDTO, or with status 404 (Not Found)
     */
    @GetMapping("/g-ps/{id}")
    @Timed
    public ResponseEntity<GPSDTO> getGPS(@PathVariable Long id) {
        log.debug("REST request to get GPS : {}", id);
        GPSDTO gPSDTO = gPSService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gPSDTO));
    }

    /**
     * DELETE  /g-ps/:id : delete the "id" gPS.
     *
     * @param id the id of the gPSDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/g-ps/{id}")
    @Timed
    public ResponseEntity<Void> deleteGPS(@PathVariable Long id) {
        log.debug("REST request to delete GPS : {}", id);
        gPSService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
