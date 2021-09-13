package com.ospedale.project.model;

import com.ospedale.project.dto.PazienteDTO;
import com.ospedale.project.enumPackage.Codice;
import com.ospedale.project.enumPackage.Sesso;
import com.ospedale.project.enumPackage.Stato;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
public class Paziente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cf;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Enumerated(EnumType.STRING)
    private Sesso sesso;
    @Column(nullable = false)
    private Instant data_nascita;
    @Column(nullable = false)
    private Instant data_entrata; //l'ultima volta che il paziente è entrato nel pronto soccorso
    private Instant data_dimissione; //l'ultima volta che il paziente è uscito dal pronto soccorso
    private String luogo_nascita;
    private String nazionalita;
    @Enumerated(EnumType.STRING)
    private Codice codice;
    private String diagnosi;
    private Boolean isActive;
    @Enumerated(EnumType.STRING)
    private Stato stato;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Documento> documenti;

    public Paziente() {
    }

    public Paziente (String cf, String nome, String cognome, Sesso sesso, Instant data_nascita, String luogo_nascita, String nazionalita, Codice codice, String diagnosi) {
        this.cf = cf;
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.data_nascita = data_nascita;
        this.data_entrata = Instant.now();
        this.data_dimissione = null;
        this.luogo_nascita = luogo_nascita;
        this.nazionalita = nazionalita;
        this.codice = codice;
        this.diagnosi = diagnosi;
        this.isActive = true;
        this.stato = Stato.ProntoSoccorso;
    }

    public Paziente(PazienteDTO pazienteDTO) {
        this.id = pazienteDTO.id;
        this.cf = pazienteDTO.cf;
        this.nome = pazienteDTO.nome;
        this.cognome = pazienteDTO.cognome;
        this.sesso = pazienteDTO.sesso;
        this.data_nascita = pazienteDTO.data_nascita;
        this.luogo_nascita = pazienteDTO.luogo_nascita;
        this.nazionalita = pazienteDTO.nazionalita;
        this.data_entrata = pazienteDTO.data_entrata;
        this.data_dimissione = pazienteDTO.data_dimissione;
        this.codice = pazienteDTO.codice;
        this.diagnosi = pazienteDTO.diagnosi;
        this.documenti = pazienteDTO.documenti;
        this.isActive = true;
        this.stato = Stato.ProntoSoccorso;
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

    public String getNazionalita() {
        return nazionalita;
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
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

    public List<Documento> getDocumenti() {
        return documenti;
    }

    public void setDocumenti(List<Documento> documenti) {
        this.documenti = documenti;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }

    public boolean confronta (PazienteDTO pazienteDTO) {
        if (this.cf == pazienteDTO.cf &&
        this.nome == pazienteDTO.nome &&
        this.cognome == pazienteDTO.cognome &&
        this.sesso == pazienteDTO.sesso &&
        this.data_nascita.equals(pazienteDTO.data_nascita) &&
        this.luogo_nascita == pazienteDTO.luogo_nascita &&
        this.nazionalita == pazienteDTO.nazionalita &&
        this.codice == pazienteDTO.codice &&
        this.diagnosi == pazienteDTO.diagnosi &&
        this.data_entrata.equals(pazienteDTO.data_entrata) &&
        this.data_dimissione == pazienteDTO.data_dimissione) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Paziente{" +
                "id=" + id +
                ", cf='" + cf + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", sesso=" + sesso +
                ", data_nascita=" + data_nascita +
                ", data_entrata=" + data_entrata +
                ", data_dimissione=" + data_dimissione +
                ", luogo_nascita='" + luogo_nascita + '\'' +
                ", nazionalita='" + nazionalita + '\'' +
                ", codice=" + codice +
                ", diagnosi='" + diagnosi + '\'' +
                ", isActive=" + isActive +
                ", stato=" + stato +
                '}';
    }
}