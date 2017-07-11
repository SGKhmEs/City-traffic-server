package com.citytraffic.server.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CarOnRoute entity.
 */
public class CarOnRouteDTO implements Serializable {

    private Long id;

    private LocalDate timeLogin;

    private LocalDate timeLogout;

    private Long carId;

    private Long routeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTimeLogin() {
        return timeLogin;
    }

    public void setTimeLogin(LocalDate timeLogin) {
        this.timeLogin = timeLogin;
    }

    public LocalDate getTimeLogout() {
        return timeLogout;
    }

    public void setTimeLogout(LocalDate timeLogout) {
        this.timeLogout = timeLogout;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long busRouteId) {
        this.routeId = busRouteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarOnRouteDTO carOnRouteDTO = (CarOnRouteDTO) o;
        if(carOnRouteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carOnRouteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarOnRouteDTO{" +
            "id=" + getId() +
            ", timeLogin='" + getTimeLogin() + "'" +
            ", timeLogout='" + getTimeLogout() + "'" +
            "}";
    }
}
