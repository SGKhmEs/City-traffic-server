package com.citytraffic.server.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * GPS date of the Route
 */
@ApiModel(description = "GPS date of the Route")
@Entity
@Table(name = "gps")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GPS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_time")
    private LocalDate time;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "accurancy")
    private Integer accurancy;

    @Column(name = "speed")
    private Integer speed;

    @ManyToOne
    private Car car;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTime() {
        return time;
    }

    public GPS time(LocalDate time) {
        this.time = time;
        return this;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public String getLatitude() {
        return latitude;
    }

    public GPS latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public GPS longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getAccurancy() {
        return accurancy;
    }

    public GPS accurancy(Integer accurancy) {
        this.accurancy = accurancy;
        return this;
    }

    public void setAccurancy(Integer accurancy) {
        this.accurancy = accurancy;
    }

    public Integer getSpeed() {
        return speed;
    }

    public GPS speed(Integer speed) {
        this.speed = speed;
        return this;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Car getCar() {
        return car;
    }

    public GPS car(Car car) {
        this.car = car;
        return this;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GPS gPS = (GPS) o;
        if (gPS.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gPS.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GPS{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", accurancy='" + getAccurancy() + "'" +
            ", speed='" + getSpeed() + "'" +
            "}";
    }
}
