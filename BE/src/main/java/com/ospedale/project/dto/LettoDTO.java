package com.ospedale.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ospedale.project.model.Letto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LettoDTO {
    private Long id;
    private Boolean stato;

    public LettoDTO () {}

    public LettoDTO(Letto letto) {
        this.id = letto.getId();
        this.stato = letto.getStato();
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
