package com.citytraffic.server.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the GPS entity.
 */
public class GPSDTO implements Serializable {

    private Long id;

    private LocalDate time;

    private String latitude;

    private String longitude;

    private Integer accurancy;

    private Integer speed;

    private Long carId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getAccurancy() {
        return accurancy;
    }

    public void setAccurancy(Integer accurancy) {
        this.accurancy = accurancy;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GPSDTO gPSDTO = (GPSDTO) o;
        if(gPSDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gPSDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GPSDTO{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", accurancy='" + getAccurancy() + "'" +
            ", speed='" + getSpeed() + "'" +
            "}";
    }
}
