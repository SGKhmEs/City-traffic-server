package com.citytraffic.server.service.mapper;

import com.citytraffic.server.domain.*;
import com.citytraffic.server.service.dto.CarOnRouteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CarOnRoute and its DTO CarOnRouteDTO.
 */
@Mapper(componentModel = "spring", uses = {CarMapper.class, BusRouteMapper.class, })
public interface CarOnRouteMapper extends EntityMapper <CarOnRouteDTO, CarOnRoute> {

    @Mapping(source = "car.id", target = "carId")

    @Mapping(source = "route.id", target = "routeId")
    CarOnRouteDTO toDto(CarOnRoute carOnRoute); 

    @Mapping(source = "carId", target = "car")

    @Mapping(source = "routeId", target = "route")
    CarOnRoute toEntity(CarOnRouteDTO carOnRouteDTO); 
    default CarOnRoute fromId(Long id) {
        if (id == null) {
            return null;
        }
        CarOnRoute carOnRoute = new CarOnRoute();
        carOnRoute.setId(id);
        return carOnRoute;
    }
}
