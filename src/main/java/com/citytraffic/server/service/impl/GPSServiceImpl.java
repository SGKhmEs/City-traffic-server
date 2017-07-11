package com.citytraffic.server.service.impl;

import com.citytraffic.server.service.GPSService;
import com.citytraffic.server.domain.GPS;
import com.citytraffic.server.repository.GPSRepository;
import com.citytraffic.server.service.dto.GPSDTO;
import com.citytraffic.server.service.mapper.GPSMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing GPS.
 */
@Service
@Transactional
public class GPSServiceImpl implements GPSService{

    private final Logger log = LoggerFactory.getLogger(GPSServiceImpl.class);

    private final GPSRepository gPSRepository;

    private final GPSMapper gPSMapper;

    public GPSServiceImpl(GPSRepository gPSRepository, GPSMapper gPSMapper) {
        this.gPSRepository = gPSRepository;
        this.gPSMapper = gPSMapper;
    }

    /**
     * Save a gPS.
     *
     * @param gPSDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GPSDTO save(GPSDTO gPSDTO) {
        log.debug("Request to save GPS : {}", gPSDTO);
        GPS gPS = gPSMapper.toEntity(gPSDTO);
        gPS = gPSRepository.save(gPS);
        return gPSMapper.toDto(gPS);
    }

    /**
     *  Get all the gPS.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GPSDTO> findAll() {
        log.debug("Request to get all GPS");
        return gPSRepository.findAll().stream()
            .map(gPSMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one gPS by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GPSDTO findOne(Long id) {
        log.debug("Request to get GPS : {}", id);
        GPS gPS = gPSRepository.findOne(id);
        return gPSMapper.toDto(gPS);
    }

    /**
     *  Delete the  gPS by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GPS : {}", id);
        gPSRepository.delete(id);
    }
}
