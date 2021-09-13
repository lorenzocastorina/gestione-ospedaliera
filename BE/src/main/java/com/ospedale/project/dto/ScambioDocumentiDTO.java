package com.ospedale.project.dto;

public class ScambioDocumentiDTO {

    private PazienteDTO pazienteDTO;
    private String nome;

    public ScambioDocumentiDTO() {}

    public ScambioDocumentiDTO(PazienteDTO pazienteDTO, String nome) {
        this.pazienteDTO = pazienteDTO;
        this.nome = nome;
    }

    public PazienteDTO getPazienteDTO() {
        return pazienteDTO;
    }

    public void setPazienteDTO(PazienteDTO pazienteDTO) {
        this.pazienteDTO = pazienteDTO;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
