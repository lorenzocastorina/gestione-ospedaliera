package com.ospedale.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ospedale.project.enumPackage.Codice;
import com.ospedale.project.enumPackage.Sesso;
import com.ospedale.project.enumPackage.Stato;
import com.ospedale.project.model.Documento;
import com.ospedale.project.model.Paziente;

import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.USE_DEFAULTS) //per avere i campi null nel json
public class PazienteDTO {
    public Long id;
    public String cf;
    public String nome;
    public String cognome;
    public Sesso sesso;
    public Instant data_nascita;
    public String luogo_nascita;
    public String nazionalita;
    public Instant data_entrata;
    public Instant data_dimissione;
    public Codice codice;
    public String diagnosi;
    public Stato stato;
    public List<Documento> documenti;

    public PazienteDTO () {}

    public PazienteDTO (Paziente paziente) {
        this.id = paziente.getId();
        this.cf = paziente.getCf();
        this.nome = paziente.getNome();
        this.cognome = paziente.getCognome();
        this.sesso = paziente.getSesso();
        this.data_nascita = paziente.getData_nascita();
        this.luogo_nascita = paziente.getLuogo_nascita();
        this.nazionalita = paziente.getNazionalita();
        this.data_entrata = paziente.getData_entrata();
        this.data_dimissione = paziente.getData_dimissione();
        this.codice = paziente.getCodice();
        this.diagnosi = paziente.getDiagnosi();
        this.documenti = paziente.getDocumenti();
        this.stato = paziente.getStato();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Sesso getSesso() {
        return sesso;
    }

    public void setSesso(Sesso sesso) {
        this.sesso = sesso;
    }

    public Instant getData_nascita() {
        return data_nascita;
    }

    public void setData_nascita(Instant data_nascita) {
        this.data_nascita = data_nascita;
    }

    public String getLuogo_nascita() {
        return luogo_nascita;
    }

    public void setLuogo_nascita(String luogo_nascita) {
        this.luogo_nascita = luogo_nascita;
    }

    public String getNazionalita() {
        return nazionalita;
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }

    public Instant getData_entrata() {
        return data_entrata;
    }

    public void setData_entrata(Instant data_entrata) {
        this.data_entrata = data_entrata;
    }

    public Instant getData_dimissione() {
        return data_dimissione;
    }

    public void setData_dimissione(Instant data_dimissione) {
        this.data_dimissione = data_dimissione;
    }

    public Codice getCodice() {
        return codice;
    }

    public void setCodice(Codice codice) {
        this.codice = codice;
    }

    public String getDiagnosi() {
        return diagnosi;
    }

    public void setDiagnosi(String diagnosi) {
        this.diagnosi = diagnosi;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }

    public List<Documento> getDocumenti() {
        return documenti;
    }

    public void setDocumenti(List<Documento> documenti) {
        this.documenti = documenti;
    }

    @Override
    public String toString() {
        return "PazienteDTO{" +
                "id=" + id +
                ", cf='" + cf + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", sesso=" + sesso +
                ", data_nascita=" + data_nascita +
                ", luogo_nascita='" + luogo_nascita + '\'' +
                ", nazionalita='" + nazionalita + '\'' +
                ", data_entrata=" + data_entrata +
                ", data_dimissione=" + data_dimissione +
                ", codice=" + codice +
                ", diagnosi='" + diagnosi + '\'' +
                ", stato=" + stato +
                '}';
    }
}
