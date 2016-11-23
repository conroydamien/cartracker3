package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Car.
 */
@Entity
@Table(name = "car")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "vin")
    private String vin;

    @Column(name = "contract_id")
    private String contractID;

    @OneToMany(mappedBy = "car")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Maintrec> maintrecs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public Car vin(String vin) {
        this.vin = vin;
        return this;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getContractID() {
        return contractID;
    }

    public Car contractID(String contractID) {
        this.contractID = contractID;
        return this;
    }

    public void setContractID(String contractID) {
        this.contractID = contractID;
    }

    public Set<Maintrec> getMaintrecs() {
        return maintrecs;
    }

    public Car maintrecs(Set<Maintrec> maintrecs) {
        this.maintrecs = maintrecs;
        return this;
    }

    public Car addMaintrec(Maintrec maintrec) {
        maintrecs.add(maintrec);
        maintrec.setCar(this);
        return this;
    }

    public Car removeMaintrec(Maintrec maintrec) {
        maintrecs.remove(maintrec);
        maintrec.setCar(null);
        return this;
    }

    public void setMaintrecs(Set<Maintrec> maintrecs) {
        this.maintrecs = maintrecs;
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
        if(car.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Car{" +
            "id=" + id +
            ", vin='" + vin + "'" +
            ", contractID='" + contractID + "'" +
            '}';
    }
}
