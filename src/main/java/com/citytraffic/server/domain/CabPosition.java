package com.citytraffic.server.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CabPosition.
 */
@Entity
@Table(name = "cab_position")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CabPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "qr")
    private String qr;

    @Column(name = "jhi_time")
    private String time;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "accuracy")
    private String accuracy;

    @Column(name = "speed")
    private String speed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQr() {
        return qr;
    }

    public CabPosition qr(String qr) {
        this.qr = qr;
        return this;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getTime() {
        return time;
    }

    public CabPosition time(String time) {
        this.time = time;
        return this;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLatitude() {
        return latitude;
    }

    public CabPosition latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public CabPosition longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public CabPosition accuracy(String accuracy) {
        this.accuracy = accuracy;
        return this;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getSpeed() {
        return speed;
    }

    public CabPosition speed(String speed) {
        this.speed = speed;
        return this;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CabPosition cabPosition = (CabPosition) o;
        if (cabPosition.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cabPosition.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CabPosition{" +
            "id=" + id +
            ", qr='" + qr + "'" +
            ", time='" + time + "'" +
            ", latitude='" + latitude + "'" +
            ", longitude='" + longitude + "'" +
            ", accuracy='" + accuracy + "'" +
            ", speed='" + speed + "'" +
            '}';
    }
}
