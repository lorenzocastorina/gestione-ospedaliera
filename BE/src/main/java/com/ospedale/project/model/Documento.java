package com.ospedale.project.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
public class Documento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Instant data_creazione;

    @ManyToOne
    @JoinColumn(name = "paziente")
    private Paziente paziente; //il paziente che nel database comparirà come id

    public Documento(String nome) {
        this.nome = nome;
        this.data_creazione = Instant.now();
    }

    public Documento() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Instant getData_creazione() {
        return data_creazione;
    }

    public void setData_creazione(Instant data_creazione) {
        this.data_creazione = data_creazione;
    }
}
