package com.citytraffic.server.service.impl;

import com.citytraffic.server.service.CarOnRouteService;
import com.citytraffic.server.domain.CarOnRoute;
import com.citytraffic.server.repository.CarOnRouteRepository;
import com.citytraffic.server.service.dto.CarOnRouteDTO;
import com.citytraffic.server.service.mapper.CarOnRouteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CarOnRoute.
 */
@Service
@Transactional
public class CarOnRouteServiceImpl implements CarOnRouteService{

    private final Logger log = LoggerFactory.getLogger(CarOnRouteServiceImpl.class);

    private final CarOnRouteRepository carOnRouteRepository;

    private final CarOnRouteMapper carOnRouteMapper;

    public CarOnRouteServiceImpl(CarOnRouteRepository carOnRouteRepository, CarOnRouteMapper carOnRouteMapper) {
        this.carOnRouteRepository = carOnRouteRepository;
        this.carOnRouteMapper = carOnRouteMapper;
    }

    /**
     * Save a carOnRoute.
     *
     * @param carOnRouteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CarOnRouteDTO save(CarOnRouteDTO carOnRouteDTO) {
        log.debug("Request to save CarOnRoute : {}", carOnRouteDTO);
        CarOnRoute carOnRoute = carOnRouteMapper.toEntity(carOnRouteDTO);
        carOnRoute = carOnRouteRepository.save(carOnRoute);
        return carOnRouteMapper.toDto(carOnRoute);
    }

    /**
     *  Get all the carOnRoutes.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CarOnRouteDTO> findAll() {
        log.debug("Request to get all CarOnRoutes");
        return carOnRouteRepository.findAll().stream()
            .map(carOnRouteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one carOnRoute by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CarOnRouteDTO findOne(Long id) {
        log.debug("Request to get CarOnRoute : {}", id);
        CarOnRoute carOnRoute = carOnRouteRepository.findOne(id);
        return carOnRouteMapper.toDto(carOnRoute);
    }

    /**
     *  Delete the  carOnRoute by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarOnRoute : {}", id);
        carOnRouteRepository.delete(id);
    }
}
