package com.ospedale.project.service;

import com.ospedale.project.dto.PazienteDTO;
import com.ospedale.project.model.Paziente;
import com.ospedale.project.repository.LettoRepository;
import com.ospedale.project.repository.PazienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PazienteService {

    @Autowired
    PazienteRepository pazienteRepository;

    @Autowired
    LettoRepository lettoRepository;

    public void delete(Optional<Paziente>  paziente) {
        paziente.get().setActive(false);
    }

    public List<Paziente> findAll() {
        return pazienteRepository.findAllByIsActiveIsTrue();
    }

    public Optional<Paziente> findEliminatiByCf(String cf) {
        return pazienteRepository.findByCfAndIsActiveIsFalse(cf);
    }

    public Optional<Paziente> findByCf(String cf) {
        return pazienteRepository.findByCfAndIsActiveIsTrue(cf);
    }

    public Optional<Paziente> findById(Long id) {
        return pazienteRepository.findByIdAndIsActiveIsTrue(id);
    }

    public void save(Paziente paziente) {
        pazienteRepository.save(paziente);
    }

    public List<Paziente> findAllProntoSoccorso() {
        return pazienteRepository.findAllProntoSoccorso();
    }

    public List<Paziente> findAllEliminati() {
        return pazienteRepository.findAllEliminati();
    }

    public List<Paziente> findAllRicoverati() {
        return pazienteRepository.findAllRicoverati();
    }

    public List<Paziente> findAllDimessi() {
        return pazienteRepository.findAllDimessi();
    }

    public void aggiornaPaziente(Paziente paziente, PazienteDTO pazienteDTO) {
        paziente.setCf(pazienteDTO.cf);
        paziente.setNome(pazienteDTO.nome);
        paziente.setCognome(pazienteDTO.cognome);
        paziente.setCodice(pazienteDTO.codice);
        paziente.setDiagnosi(pazienteDTO.diagnosi);
        paziente.setLuogo_nascita(pazienteDTO.luogo_nascita);
        paziente.setSesso(pazienteDTO.sesso);
        paziente.setNazionalita(pazienteDTO.nazionalita);
    }

    //Funzione che controlla che i campi obbligatori del paziente siano stati inseriti
    public Boolean checkMandatoryArguments(PazienteDTO pazienteDTO) {
        if (pazienteDTO.nome == null || pazienteDTO.cf == null || pazienteDTO.cognome == null || pazienteDTO.data_nascita == null || pazienteDTO.data_entrata == null) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkMandatoryArguments(Paziente paziente) {
        if (paziente.getCf() == null || paziente.getNome() == null || paziente.getCognome() == null || paziente.getData_nascita() == null || paziente.getData_entrata() == null) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkIfPazienteIsInLista (String cf, List<Paziente> pazienti) {
        for (Paziente p: pazienti) {
            if (cf.equals(p.getCf())){
                return true;
            }
        }
        return false;
    }

}
