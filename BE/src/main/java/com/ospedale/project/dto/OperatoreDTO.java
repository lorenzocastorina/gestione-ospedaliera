package com.ospedale.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ospedale.project.model.Operatore;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperatoreDTO {
    public Long id;
    public String username;

    public OperatoreDTO (Operatore operatore){
        this.id = operatore.getId();
        this.username = operatore.getUsername();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
