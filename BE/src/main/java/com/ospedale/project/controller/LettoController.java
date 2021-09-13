package com.ospedale.project.controller;

import com.ospedale.project.dto.LettoDTO;
import com.ospedale.project.model.Letto;
import com.ospedale.project.service.LettoService;
import com.ospedale.project.service.RicoveroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*") //Per fare le richieste sulla stessa rete del frontend, "*" significa tutte
@RestController
@RequestMapping("/letto")
public class LettoController {

    @Autowired
    LettoService lettoService;

    @Autowired
    RicoveroService ricoveroService;

    @PostMapping("/toggleStato")
    public LettoDTO cambiaStato (@RequestBody Long id, Boolean stato) {
        //Trovo il letto selezionato nel Db
        Optional<Letto> letto = lettoService.findById(id);

        //Cambio lo stato e salvo le modifiche
        letto.get().setStato(stato);
        lettoService.save(letto.get());
        return new LettoDTO(letto.get());
    }

    @GetMapping("/getLetti")
    public List<LettoDTO> getLetti () {
        //Ritorno la lista di letti
        return lettoService.findAll().stream().map(LettoDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/liberi")
    public List<LettoDTO> getLettiLiberi () {
        //Ritorno la lista di letti liberi
        return lettoService.findAllLiberi().stream().map(LettoDTO::new).collect(Collectors.toList());
    }
}
