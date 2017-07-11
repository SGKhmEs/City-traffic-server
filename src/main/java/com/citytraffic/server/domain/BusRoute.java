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
 * The Route of car
 */
@ApiModel(description = "The Route of car")
@Entity
@Table(name = "bus_route")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BusRoute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "route")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CarOnRoute> carOnRoutes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BusRoute name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CarOnRoute> getCarOnRoutes() {
        return carOnRoutes;
    }

    public BusRoute carOnRoutes(Set<CarOnRoute> carOnRoutes) {
        this.carOnRoutes = carOnRoutes;
        return this;
    }

    public BusRoute addCarOnRoute(CarOnRoute carOnRoute) {
        this.carOnRoutes.add(carOnRoute);
        carOnRoute.setRoute(this);
        return this;
    }

    public BusRoute removeCarOnRoute(CarOnRoute carOnRoute) {
        this.carOnRoutes.remove(carOnRoute);
        carOnRoute.setRoute(null);
        return this;
    }

    public void setCarOnRoutes(Set<CarOnRoute> carOnRoutes) {
        this.carOnRoutes = carOnRoutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BusRoute busRoute = (BusRoute) o;
        if (busRoute.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), busRoute.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BusRoute{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
