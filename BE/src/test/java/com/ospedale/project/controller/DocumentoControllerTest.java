package com.ospedale.project.controller;

import com.ospedale.project.dto.PazienteDTO;
import com.ospedale.project.dto.ScambioDocumentiDTO;
import com.ospedale.project.enumPackage.Codice;
import com.ospedale.project.enumPackage.Sesso;
import com.ospedale.project.enumPackage.Stato;
import com.ospedale.project.model.Letto;
import com.ospedale.project.model.Paziente;
import com.ospedale.project.model.Ricovero;
import com.ospedale.project.repository.LettoRepository;
import com.ospedale.project.repository.PazienteRepository;
import com.ospedale.project.repository.RicoveroRepository;
import com.ospedale.project.service.PazienteService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DocumentoControllerTest {

    @Autowired
    DocumentoController documentoController;
    @Autowired
    PazienteService pazienteService;
    @Autowired
    PazienteRepository pazienteRepository;
    @Autowired
    RicoveroRepository ricoveroRepository;
    @Autowired
    LettoRepository lettoRepository;

    @BeforeEach
    public void init() {inizializeDb();}

    @AfterEach
    public void destroy () {deleteDb();}

    @Test
    public void testAddDocumento () {
        //Paziente è null
        PazienteDTO pazienteDTO1 = null;
        String nomeDocumento = "UrlFile";
        ScambioDocumentiDTO scambioDocumentiDTO1 = new ScambioDocumentiDTO(pazienteDTO1, nomeDocumento);
        assertThrows(ResponseStatusException.class,
                () -> documentoController.addDocumento(scambioDocumentiDTO1));

        //File è null
        Optional<Paziente> paziente = pazienteService.findByCf("ACXNOF76J35I879K");
        pazienteDTO1 = new PazienteDTO(paziente.get());
        nomeDocumento = null;
        ScambioDocumentiDTO scambioDocumentiDTO2 = new ScambioDocumentiDTO(pazienteDTO1, nomeDocumento);
        assertThrows(ResponseStatusException.class,
                () -> documentoController.addDocumento(scambioDocumentiDTO2));

        //Se il paziente è presente e il documento è presente
        nomeDocumento = "UrlFile";
        ScambioDocumentiDTO scambioDocumentiDTO3 = new ScambioDocumentiDTO(pazienteDTO1, nomeDocumento);
        documentoController.addDocumento(scambioDocumentiDTO3);
        Optional<Paziente> pazienteControllo = pazienteService.findByCf(scambioDocumentiDTO3.getPazienteDTO().getCf());
        assertNotNull(pazienteControllo.get().getDocumenti());
        assertFalse(pazienteControllo.get().getDocumenti().isEmpty());
    }

    public void inizializeDb() {
        //Creazione pazienti
        Paziente paziente1 = new Paziente("ACXNOF76J35I879K", "Paolo", "Ruggiero", Sesso.M, Instant.now(), "Trapani", "Uruguay", Codice.GIALLO, "Frattura dolorosa");
        Paziente paziente2 = new Paziente("BCXNOF76J35I879T", "Andrea", "Rossi", Sesso.M, Instant.now(), "Trani", "Italia", Codice.VERDE, "Frattura molto dolorosa");
        Paziente paziente3 = new Paziente("CCXNOF76J35I879P", "Vincenzo", "Bianchi", Sesso.F, Instant.now(), "Lecce", "Italia", Codice.ROSSO, "Frattura poco dolorosa");
        Paziente paziente4 = new Paziente("DCXNOF76J35I879L", "Antonio", "Dimesso", Sesso.F, Instant.now(), "Brindisi", "Grecia", Codice.BIANCO, "Frattura un pò dolorosa");
        Paziente paziente5 = new Paziente("DCXNUU76J35I879L", "Antonio", "Ricoverato", Sesso.F, Instant.now(), "Brindisi", "Grecia", Codice.ROSSO, "Frattura un pò dolorosa");

        //Set caratteristiche
        paziente4.setStato(Stato.Dimesso);
        paziente5.setStato(Stato.Ricoverato);

        //Aggiunta lista al Database
        List<Paziente> pazienti = new ArrayList<>();
        pazienti.add(paziente1);
        pazienti.add(paziente2);
        pazienti.add(paziente3);
        pazienti.add(paziente4);
        pazienti.add(paziente5);
        pazienteRepository.saveAll(pazienti);

        //Creazione letti
        Letto letto1 = new Letto(Boolean.FALSE);
        Letto letto2 = new Letto(Boolean.TRUE);
        Letto letto3 = new Letto(Boolean.FALSE);

        //Aggiunta lista al Database
        List<Letto> letti = new ArrayList<>();
        letti.add(letto1);
        letti.add(letto2);
        letti.add(letto3);
        lettoRepository.saveAll(letti);

        //Creazione ricoveri
        Ricovero ricovero1 = new Ricovero(letto1, paziente5);
        Ricovero ricovero2 = new Ricovero(letto2, paziente5);

        //Set caratteristiche
        ricovero1.setInizio_ricovero(Instant.now());
        ricovero1.setFine_ricovero(Instant.now());
        ricovero2.setInizio_ricovero(Instant.now());

        //Aggiunta lista al Database
        List<Ricovero> ricoveri = new ArrayList<>();
        ricoveri.add(ricovero1);
        ricoveri.add(ricovero2);
        ricoveroRepository.saveAll(ricoveri);
    }

    public void deleteDb() {
        //Elimino tutti gli elementi dal Database
        ricoveroRepository.deleteAll();
        lettoRepository.deleteAll();
        pazienteRepository.deleteAll();
    }

}
