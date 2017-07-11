package com.citytraffic.server.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Cars on Route
 */
@ApiModel(description = "Cars on Route")
@Entity
@Table(name = "car_on_route")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CarOnRoute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "time_login")
    private LocalDate timeLogin;

    @Column(name = "time_logout")
    private LocalDate timeLogout;

    @ManyToOne
    private Car car;

    @ManyToOne
    private BusRoute route;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTimeLogin() {
        return timeLogin;
    }

    public CarOnRoute timeLogin(LocalDate timeLogin) {
        this.timeLogin = timeLogin;
        return this;
    }

    public void setTimeLogin(LocalDate timeLogin) {
        this.timeLogin = timeLogin;
    }

    public LocalDate getTimeLogout() {
        return timeLogout;
    }

    public CarOnRoute timeLogout(LocalDate timeLogout) {
        this.timeLogout = timeLogout;
        return this;
    }

    public void setTimeLogout(LocalDate timeLogout) {
        this.timeLogout = timeLogout;
    }

    public Car getCar() {
        return car;
    }

    public CarOnRoute car(Car car) {
        this.car = car;
        return this;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public BusRoute getRoute() {
        return route;
    }

    public CarOnRoute route(BusRoute busRoute) {
        this.route = busRoute;
        return this;
    }

    public void setRoute(BusRoute busRoute) {
        this.route = busRoute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarOnRoute carOnRoute = (CarOnRoute) o;
        if (carOnRoute.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carOnRoute.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarOnRoute{" +
            "id=" + getId() +
            ", timeLogin='" + getTimeLogin() + "'" +
            ", timeLogout='" + getTimeLogout() + "'" +
            "}";
    }
}
