package com.citytraffic.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The Car entity
 */
@ApiModel(description = "The Car entity")
@Entity
@Table(name = "car")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "qr")
    private String qr;

    @OneToMany(mappedBy = "car")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CarOnRoute> carOnRoutes = new HashSet<>();

    @OneToMany(mappedBy = "car")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GPS> gps = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQr() {
        return qr;
    }

    public Car qr(String qr) {
        this.qr = qr;
        return this;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public Set<CarOnRoute> getCarOnRoutes() {
        return carOnRoutes;
    }

    public Car carOnRoutes(Set<CarOnRoute> carOnRoutes) {
        this.carOnRoutes = carOnRoutes;
        return this;
    }

    public Car addCarOnRoute(CarOnRoute carOnRoute) {
        this.carOnRoutes.add(carOnRoute);
        carOnRoute.setCar(this);
        return this;
    }

    public Car removeCarOnRoute(CarOnRoute carOnRoute) {
        this.carOnRoutes.remove(carOnRoute);
        carOnRoute.setCar(null);
        return this;
    }

    public void setCarOnRoutes(Set<CarOnRoute> carOnRoutes) {
        this.carOnRoutes = carOnRoutes;
    }

    public Set<GPS> getGps() {
        return gps;
    }

    public Car gps(Set<GPS> gPS) {
        this.gps = gPS;
        return this;
    }

    public Car addGps(GPS gPS) {
        this.gps.add(gPS);
        gPS.setCar(this);
        return this;
    }

    public Car removeGps(GPS gPS) {
        this.gps.remove(gPS);
        gPS.setCar(null);
        return this;
    }

    public void setGps(Set<GPS> gPS) {
        this.gps = gPS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        if (car.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), car.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Car{" +
            "id=" + getId() +
            ", qr='" + getQr() + "'" +
            "}";
    }
}
