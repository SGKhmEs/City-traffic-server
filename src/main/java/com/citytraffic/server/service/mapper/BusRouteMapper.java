package com.citytraffic.server.service.mapper;

import com.citytraffic.server.domain.*;
import com.citytraffic.server.service.dto.BusRouteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BusRoute and its DTO BusRouteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BusRouteMapper extends EntityMapper <BusRouteDTO, BusRoute> {
    
    @Mapping(target = "carOnRoutes", ignore = true)
    BusRoute toEntity(BusRouteDTO busRouteDTO); 
    default BusRoute fromId(Long id) {
        if (id == null) {
            return null;
        }
        BusRoute busRoute = new BusRoute();
        busRoute.setId(id);
        return busRoute;
    }
}
