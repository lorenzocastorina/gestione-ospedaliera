package com.ospedale.project.service;

import com.ospedale.project.model.Ricovero;
import com.ospedale.project.repository.RicoveroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RicoveroService {

    @Autowired
    RicoveroRepository ricoveroRepository;

    public void save (Ricovero ricovero) {
        ricoveroRepository.save(ricovero);
    }

    public Optional<Ricovero> findRicoveroNonTerminatoByIdLetto(Long id) {
        return ricoveroRepository.findRicoveroNonTerminatoByIdLetto(id);
    }

    public Optional<Ricovero> findRicoveroByPazienteId(Long id) {
        return ricoveroRepository.findRicoveroByPaziente_Id(id);
    }

    public Optional<Ricovero> findUltimoRicoveroAttivoByPazienteId (Long id) {
        return ricoveroRepository.findUltimoRicoveroAttivoByPazienteId (id);
    }
    public List<Ricovero> findUltimoRicoveroNonAttivoByPazienteId(Long id, Pageable pageable) {
        return ricoveroRepository.findUltimoRicoveroNonAttivoByPazienteId(id, pageable);
    }
}
