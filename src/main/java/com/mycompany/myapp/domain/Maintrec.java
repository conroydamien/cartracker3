package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Maintrec.
 */
@Entity
@Table(name = "maintrec")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Maintrec implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "milage")
    private Integer milage;

    @Column(name = "notes")
    private String notes;

    @ManyToOne
    private Car car;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMilage() {
        return milage;
    }

    public Maintrec milage(Integer milage) {
        this.milage = milage;
        return this;
    }

    public void setMilage(Integer milage) {
        this.milage = milage;
    }

    public String getNotes() {
        return notes;
    }

    public Maintrec notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Car getCar() {
        return car;
    }

    public Maintrec car(Car car) {
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
        Maintrec maintrec = (Maintrec) o;
        if(maintrec.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, maintrec.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Maintrec{" +
            "id=" + id +
            ", milage='" + milage + "'" +
            ", notes='" + notes + "'" +
            '}';
    }
}
