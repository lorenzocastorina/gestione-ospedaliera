package com.ospedale.project.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Ricovero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    @ManyToOne //un paziente può avere più ricoveri
    private Paziente paziente;
    @ManyToOne
    private Letto letto;
    @Column(nullable = false)
    private Instant inizio_ricovero;
    private Instant fine_ricovero;

    public Ricovero () {}

    public Ricovero (Letto letto, Paziente paziente) {
        this.paziente = paziente;
        this.letto = letto;
        this.inizio_ricovero = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paziente getPaziente() {
        return paziente;
    }

    public void setPaziente(Paziente paziente) {
        this.paziente = paziente;
    }

    public Letto getLetto() {
        return letto;
    }

    public void setLetto(Letto letto) {
        this.letto = letto;
    }

    public Instant getInizio_ricovero() {
        return inizio_ricovero;
    }

    public void setInizio_ricovero(Instant inizio_ricovero) {
        this.inizio_ricovero = inizio_ricovero;
    }

    public Instant getFine_ricovero() {
        return fine_ricovero;
    }

    public void setFine_ricovero(Instant fine_ricovero) {
        this.fine_ricovero = fine_ricovero;
    }
}
