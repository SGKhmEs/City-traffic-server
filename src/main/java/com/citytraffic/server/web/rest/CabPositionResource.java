package com.citytraffic.server.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.citytraffic.server.domain.CabPosition;

import com.citytraffic.server.repository.CabPositionRepository;
import com.citytraffic.server.web.rest.util.HeaderUtil;
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
 * REST controller for managing CabPosition.
 */
@RestController
@RequestMapping("/api")
public class CabPositionResource {

    private final Logger log = LoggerFactory.getLogger(CabPositionResource.class);

    private static final String ENTITY_NAME = "cabPosition";
        
    private final CabPositionRepository cabPositionRepository;

    public CabPositionResource(CabPositionRepository cabPositionRepository) {
        this.cabPositionRepository = cabPositionRepository;
    }

    /**
     * POST  /cab-positions : Create a new cabPosition.
     *
     * @param cabPosition the cabPosition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cabPosition, or with status 400 (Bad Request) if the cabPosition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cab-positions")
    @Timed
    public ResponseEntity<CabPosition> createCabPosition(@RequestBody CabPosition cabPosition) throws URISyntaxException {
        log.debug("REST request to save CabPosition : {}", cabPosition);
        if (cabPosition.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cabPosition cannot already have an ID")).body(null);
        }
        CabPosition result = cabPositionRepository.save(cabPosition);
        return ResponseEntity.created(new URI("/api/cab-positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cab-positions : Updates an existing cabPosition.
     *
     * @param cabPosition the cabPosition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cabPosition,
     * or with status 400 (Bad Request) if the cabPosition is not valid,
     * or with status 500 (Internal Server Error) if the cabPosition couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cab-positions")
    @Timed
    public ResponseEntity<CabPosition> updateCabPosition(@RequestBody CabPosition cabPosition) throws URISyntaxException {
        log.debug("REST request to update CabPosition : {}", cabPosition);
        if (cabPosition.getId() == null) {
            return createCabPosition(cabPosition);
        }
        CabPosition result = cabPositionRepository.save(cabPosition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cabPosition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cab-positions : get all the cabPositions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cabPositions in body
     */
    @GetMapping("/cab-positions")
    @Timed
    public List<CabPosition> getAllCabPositions() {
        log.debug("REST request to get all CabPositions");
        List<CabPosition> cabPositions = cabPositionRepository.findAll();
        return cabPositions;
    }

    /**
     * GET  /cab-positions/:id : get the "id" cabPosition.
     *
     * @param id the id of the cabPosition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cabPosition, or with status 404 (Not Found)
     */
    @GetMapping("/cab-positions/{id}")
    @Timed
    public ResponseEntity<CabPosition> getCabPosition(@PathVariable Long id) {
        log.debug("REST request to get CabPosition : {}", id);
        CabPosition cabPosition = cabPositionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cabPosition));
    }

    /**
     * DELETE  /cab-positions/:id : delete the "id" cabPosition.
     *
     * @param id the id of the cabPosition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cab-positions/{id}")
    @Timed
    public ResponseEntity<Void> deleteCabPosition(@PathVariable Long id) {
        log.debug("REST request to delete CabPosition : {}", id);
        cabPositionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
