package com.citytraffic.server.service.impl;

import com.citytraffic.server.service.BusRouteService;
import com.citytraffic.server.domain.BusRoute;
import com.citytraffic.server.repository.BusRouteRepository;
import com.citytraffic.server.service.dto.BusRouteDTO;
import com.citytraffic.server.service.mapper.BusRouteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing BusRoute.
 */
@Service
@Transactional
public class BusRouteServiceImpl implements BusRouteService{

    private final Logger log = LoggerFactory.getLogger(BusRouteServiceImpl.class);

    private final BusRouteRepository busRouteRepository;

    private final BusRouteMapper busRouteMapper;

    public BusRouteServiceImpl(BusRouteRepository busRouteRepository, BusRouteMapper busRouteMapper) {
        this.busRouteRepository = busRouteRepository;
        this.busRouteMapper = busRouteMapper;
    }

    /**
     * Save a busRoute.
     *
     * @param busRouteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BusRouteDTO save(BusRouteDTO busRouteDTO) {
        log.debug("Request to save BusRoute : {}", busRouteDTO);
        BusRoute busRoute = busRouteMapper.toEntity(busRouteDTO);
        busRoute = busRouteRepository.save(busRoute);
        return busRouteMapper.toDto(busRoute);
    }

    /**
     *  Get all the busRoutes.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BusRouteDTO> findAll() {
        log.debug("Request to get all BusRoutes");
        return busRouteRepository.findAll().stream()
            .map(busRouteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one busRoute by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BusRouteDTO findOne(Long id) {
        log.debug("Request to get BusRoute : {}", id);
        BusRoute busRoute = busRouteRepository.findOne(id);
        return busRouteMapper.toDto(busRoute);
    }

    /**
     *  Delete the  busRoute by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BusRoute : {}", id);
        busRouteRepository.delete(id);
    }
}
