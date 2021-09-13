package com.ospedale.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


//I letti nel sistema sono 24
@Entity
public class Letto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean stato; //0 libero, 1 occupato

    public Letto() {}

    public Letto (Boolean stato) {
        this.stato = stato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStato() {
        return stato;
    }

    public void setStato(Boolean stato) {
        this.stato = stato;
    }
}
