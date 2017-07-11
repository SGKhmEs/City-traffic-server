package com.citytraffic.server.service.mapper;

import com.citytraffic.server.domain.*;
import com.citytraffic.server.service.dto.GPSDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GPS and its DTO GPSDTO.
 */
@Mapper(componentModel = "spring", uses = {CarMapper.class, })
public interface GPSMapper extends EntityMapper <GPSDTO, GPS> {

    @Mapping(source = "car.id", target = "carId")
    GPSDTO toDto(GPS gPS); 

    @Mapping(source = "carId", target = "car")
    GPS toEntity(GPSDTO gPSDTO); 
    default GPS fromId(Long id) {
        if (id == null) {
            return null;
        }
        GPS gPS = new GPS();
        gPS.setId(id);
        return gPS;
    }
}
