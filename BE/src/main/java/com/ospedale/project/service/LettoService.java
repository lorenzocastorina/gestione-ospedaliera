package com.ospedale.project.service;

import com.ospedale.project.model.Letto;
import com.ospedale.project.repository.LettoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LettoService {

    @Autowired
    LettoRepository lettoRepository;

    public Optional<Letto> findById (Long id) {
        return lettoRepository.findById(id);
    }

    public Optional<Letto> findFirstByStatoFalse() {
        return lettoRepository.findFirstByStatoFalse();
    }

    public Optional<Letto> findFirstByStatoTrue () {
        return lettoRepository.findFirstByStatoTrue();
    }

    public List<Letto> findAll() {
        return lettoRepository.findAll();
    }

    public List<Letto> findAllLiberi() {
        return lettoRepository.findAllByStatoIsFalse();
    }

    public void save (Letto letto) {
        lettoRepository.save(letto);
    }

}
