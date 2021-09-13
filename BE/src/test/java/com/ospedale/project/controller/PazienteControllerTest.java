package com.ospedale.project.controller;

import com.ospedale.project.dto.PazienteDTO;
import com.ospedale.project.enumPackage.Codice;
import com.ospedale.project.enumPackage.Sesso;
import com.ospedale.project.enumPackage.Stato;
import com.ospedale.project.model.Letto;
import com.ospedale.project.model.Paziente;
import com.ospedale.project.model.Ricovero;
import com.ospedale.project.repository.LettoRepository;
import com.ospedale.project.repository.PazienteRepository;
import com.ospedale.project.repository.RicoveroRepository;
import com.ospedale.project.service.LettoService;
import com.ospedale.project.service.PazienteService;
import com.ospedale.project.service.RicoveroService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PazienteControllerTest {

    @Autowired
    PazienteController pazienteController;
    @Autowired
    PazienteService pazienteService;
    @Autowired
    PazienteRepository  pazienteRepository;

    @Autowired
    RicoveroService ricoveroService;
    @Autowired
    RicoveroRepository ricoveroRepository;

    @Autowired
    LettoService lettoService;
    @Autowired
    LettoRepository lettoRepository;

    @BeforeEach
    public void init() {
        inizializeDb();
    }

    @AfterEach
    public void destroy () {deleteDb();}

    @Test
    public void testGetPazienti() {
        //Lista pazienti nel Database e Lista pazienti ottenuta con la chiamata all'API
        List<PazienteDTO> pazientiDTO = pazienteController.getPazienti();
        List<Paziente> pazienti = pazienteRepository.findAll();

        //Verifico gli Assert
        assertNotNull(pazienti);
        assertFalse(pazienti.isEmpty());
        assertNotNull(pazientiDTO);
        assertFalse(pazientiDTO.isEmpty());
        assertSame(pazientiDTO.size(), pazienti.size());

        //Converto PazienteDTO e Paziente in due liste di stringhe con i codici fiscali
        List<String> pazientiDTOCf = pazientiDTO.stream().map(PazienteDTO::getCf).collect(Collectors.toList());
        List<String> pazientiCf = pazienti.stream().map(Paziente::getCf).collect(Collectors.toList());

        //Verifico confrontando il cf se tutti i pazienti sono stati inseriti correttamente
        pazientiDTOCf.forEach(cf -> assertTrue(pazientiCf.contains(cf)));
    }

    @Test
    public void testGetPazientiProntoSoccorso() {
        //Lista pazienti in Pronto Soccorso nel Database e Lista pazienti ottenuta con la chiamata all'API
        List<PazienteDTO> pazientiDTO = pazienteController.getPazientiProntoSoccorso();
        List<Paziente> pazienti = pazienteRepository.findAllProntoSoccorso();

        //Verifico gli Assert
        assertNotNull(pazienti);
        assertFalse(pazienti.isEmpty());
        assertNotNull(pazientiDTO);
        assertFalse(pazientiDTO.isEmpty());
        assertSame(pazientiDTO.size(), pazienti.size());

        //Verifico che siano tutti in ProntoSoccorso
        List<Stato> pazientiStato = pazienti.stream().map(p -> p.getStato()).collect(Collectors.toList());
        assertTrue(pazientiStato.stream().allMatch(s -> s.equals(Stato.ProntoSoccorso)));
    }

    @Test
    public void testGetPazientiRicovero() {
        //Lista pazienti ricoverati nel Database e Lista pazienti ottenuta con la chiamata all'API
        List<PazienteDTO> pazientiDTO = pazienteController.getPazientiRicoverati();
        List<Paziente> pazienti = pazienteRepository.findAllRicoverati();

        //Verifico gli Assert
        assertNotNull(pazienti);
        assertFalse(pazienti.isEmpty());
        assertNotNull(pazientiDTO);
        assertFalse(pazientiDTO.isEmpty());
        assertSame(pazientiDTO.size(), pazienti.size());

        //Verifico che siano tutti in ricovero
        List<Stato> pazientiStato = pazienti.stream().map(p -> p.getStato()).collect(Collectors.toList());
        assertTrue(pazientiStato.stream().allMatch(s -> s.equals(Stato.Ricoverato)));
    }

    @Test
    public void testGetPazientiDimessi() {
        //Lista pazienti dimessi nel Database e Lista pazienti ottenuta con la chiamata all'API
        List<PazienteDTO> pazientiDTO = pazienteController.getPazientiDimessi();
        List<Paziente> pazienti = pazienteRepository.findAllDimessi();

        //Verifico gli Assert
        assertNotNull(pazienti);
        assertFalse(pazienti.isEmpty());
        assertNotNull(pazientiDTO);
        assertFalse(pazientiDTO.isEmpty());
        assertSame(pazientiDTO.size(), pazienti.size());

        //Verifico che siano tutti dimessi
        List<Stato> pazientiStato = pazienti.stream().map(p -> p.getStato()).collect(Collectors.toList());
        assertTrue(pazientiStato.stream().allMatch(s -> s.equals(Stato.Dimesso)));
    }

    @Test
    public void testAddPazienteNuovo () {
        //Paziente nuovo
        PazienteDTO pazienteDTO = getPazienteDTOFromBody();
        assertTrue(pazienteService.checkMandatoryArguments(pazienteDTO));

        //Salvo il paziente ricevuto nel Db
        Paziente paziente = new Paziente(pazienteDTO);
        assertTrue(pazienteController.addPaziente(pazienteDTO));

        //Prendo il paziente dal Database e verifico gli Assert
        Optional<Paziente> pazienteDb = pazienteService.findByCf(paziente.getCf());
        assertTrue(pazienteDb.isPresent());
        assertTrue(pazienteDb.get().confronta(pazienteDTO));
        assertNotNull(pazienteDb.get().getData_entrata());
        assertNull(pazienteDb.get().getData_dimissione());
        assertTrue(pazienteDb.get().getStato() == Stato.ProntoSoccorso);
    }

    @Test
    public void testAddPazienteDimesso () {
        //Paziente è stato dimesso in passato
        PazienteDTO pazienteDTO = getPazienteDTODiProva("PROVAA11A0035A000A");
        assertTrue(pazienteService.checkMandatoryArguments(pazienteDTO));

        //Lo aggiungo al Database
        Paziente paziente = new Paziente(pazienteDTO);
        paziente.setStato(Stato.Dimesso);
        assertTrue(pazienteController.addPaziente(pazienteDTO));

        //Prendo il paziente dal Database e verifico gli Assert
        Optional<Paziente> pazienteDb = pazienteService.findByCf(paziente.getCf());
        assertTrue(pazienteDb.isPresent());
        assertTrue(pazienteDb.get().confronta(pazienteDTO));
        assertNotNull(pazienteDb.get().getData_entrata());
        assertNull(pazienteDb.get().getData_dimissione());
        assertTrue(pazienteDb.get().getStato() == Stato.ProntoSoccorso);
    }

    @Test
    public void testAddPazienteProntoSoccorso () {
        //Paziente è nel pronto soccorso
        PazienteDTO pazienteDTO = getPazienteDTODiProva("ACXNOF76J35I879K");
        assertTrue(pazienteService.checkMandatoryArguments(pazienteDTO));

        //Lo aggiungo al Database
        Paziente paziente = new Paziente(pazienteDTO);
        paziente.setStato(Stato.ProntoSoccorso);
        assertThrows(ResponseStatusException.class,
                ()-> pazienteController.addPaziente(pazienteDTO));
    }

    @Test
    public void testAddPazienteRicoverato () {
        //Paziente è nei ricoverati
        PazienteDTO pazienteDTO = getPazienteDTODiProva("ACXNOF76J35I879K");
        assertTrue(pazienteService.checkMandatoryArguments(pazienteDTO));

        //Lo aggiungo al Database
        Paziente paziente = new Paziente(pazienteDTO);
        paziente.setStato(Stato.Ricoverato);
        assertThrows(ResponseStatusException.class,
                ()-> pazienteController.addPaziente(pazienteDTO));
    }

    @Test
    public void testAddPazienteEliminato () {
        //Paziente è stato eliminato in passato
        PazienteDTO pazienteDTO = getPazienteDTODiProva("PROVAA44A0035A000A");
        assertTrue(pazienteService.checkMandatoryArguments(pazienteDTO));

        //Lo aggiungo al Database
        Paziente paziente = new Paziente(pazienteDTO);
        paziente.setActive(false);
        assertTrue(pazienteController.addPaziente(pazienteDTO));

        //Prendo il paziente dal Database e verifico gli Assert
        Optional<Paziente> pazienteDb = pazienteService.findByCf(paziente.getCf());
        assertTrue(pazienteDb.isPresent());
        assertTrue(pazienteDb.get().confronta(pazienteDTO));
        assertTrue(pazienteDb.get().getActive());
        assertNotNull(pazienteDb.get().getData_entrata());
        assertNull(pazienteDb.get().getData_dimissione());
        assertTrue(pazienteDb.get().getStato() == Stato.ProntoSoccorso);
    }

    @Test
    public void testEliminaPazientePresente () {
        //Paziente presente nel Database ma non ancora dimesso
        PazienteDTO pazienteDTO = getPazienteDTODiProva("ACXNOF76J35I879K");
        Optional<Paziente> paziente = pazienteService.findByCf(pazienteDTO.cf);
        assertTrue(paziente.isPresent());
        PazienteDTO finalPazienteDTO = pazienteDTO;
        assertThrows(ResponseStatusException.class,
                ()-> pazienteController.eliminaPaziente(finalPazienteDTO));

        //Paziente dimesso
        paziente.get().setStato(Stato.Dimesso);
        pazienteService.save(paziente.get());
        pazienteDTO = new PazienteDTO(paziente.get());
        paziente = pazienteService.findByCf(pazienteDTO.cf);
        assertTrue(paziente.isPresent());
        assertTrue(paziente.get().getActive());
        assertTrue(pazienteController.eliminaPaziente(pazienteDTO));
    }

    @Test
    public void testEliminaPazienteNonPresente () {
        //Paziente non presente nel Database
        PazienteDTO pazienteDTO = getPazienteDTODiProva("AVVVVF76J35I879K");
        Optional<Paziente> paziente = pazienteService.findByCf(pazienteDTO.cf);
        assertFalse(paziente.isPresent());
        assertThrows(ResponseStatusException.class,
                ()-> pazienteController.eliminaPaziente(pazienteDTO));
    }

    @Test
    public void testDimettiPazienteDalProntoSoccorso () {
        //Paziente nel Pronto Soccorso
        Optional<Paziente> pazienteProntoSoccorso = pazienteService.findByCf("ACXNOF76J35I879K"); //è in pronto soccorso
        PazienteDTO pazienteDTOProntoSoccorso = new PazienteDTO(pazienteProntoSoccorso.get());
        assertTrue(pazienteProntoSoccorso.isPresent());
        assertTrue(pazienteProntoSoccorso.get().getStato().equals(Stato.ProntoSoccorso));

        //Dimetto e verifico gli Assert
        assertTrue(pazienteController.dimettiPaziente(pazienteDTOProntoSoccorso));
        pazienteProntoSoccorso = pazienteService.findByCf(pazienteProntoSoccorso.get().getCf());
        assertTrue(pazienteProntoSoccorso.get().getStato().equals(Stato.Dimesso));
        assertNotNull(pazienteProntoSoccorso.get().getData_dimissione());
    }

    @Test
    public void testDimettiPazienteDimesso () {

        //Prendo un paziente dimesso
        Optional<Paziente> pazienteDimesso = pazienteService.findByCf("DCXNOF76J35I879L");
        assertTrue(pazienteDimesso.isPresent());
        assertTrue(pazienteDimesso.get().getStato().equals(Stato.Dimesso));

        //Dimetto
        PazienteDTO pazienteDimessoDTO = new PazienteDTO(pazienteDimesso.get());
        assertThrows(ResponseStatusException.class,
                ()-> pazienteController.dimettiPaziente(pazienteDimessoDTO));
    }

    @Test
    public void testRicoveraPazienteConLettiOccupati () {
        //Occupo l'unico letto presente nel Database
        List<Letto> letti = lettoService.findAllLiberi();
        letti.forEach(l -> l.setStato(Boolean.TRUE));
        lettoRepository.saveAll(letti);

        //Ricovero
        PazienteDTO pazienteDTO = new PazienteDTO(pazienteRepository.findAllProntoSoccorso().get(0));
        assertThrows(ResponseStatusException.class,
                ()-> pazienteController.ricoveraPaziente(pazienteDTO));
    }

    @Test
    public void testRicoveraPazienteInProntoSoccorso () {
        //Prendo il primo paziente del Pronto Soccorso e aggiungo un letto vuoto al Database
        Optional<Paziente> paziente = pazienteService.findByCf(pazienteService.findAllProntoSoccorso().get(0).getCf());
        assertTrue(paziente.isPresent());
        assertTrue(paziente.get().getStato().equals(Stato.ProntoSoccorso));
        Letto letto = new Letto(Boolean.FALSE);
        lettoService.save(letto);

        //Ricovero
        PazienteDTO pazienteDTO = new PazienteDTO(paziente.get());
        pazienteController.ricoveraPaziente(pazienteDTO);

        //Prelievo gli oggetti del ricovero inserito dal Database
        Optional<Paziente> pazienteRicovero = pazienteService.findByCf(pazienteDTO.getCf());
        Optional<Ricovero> ricovero = ricoveroService.findRicoveroByPazienteId(paziente.get().getId());
        Optional<Letto> lettoRicovero = lettoService.findById(ricovero.get().getLetto().getId());

        //Verifico gli Assert
        assertTrue(ricovero.isPresent());
        assertTrue(pazienteRicovero.isPresent());
        assertTrue(lettoRicovero.isPresent());
        assertTrue(pazienteRicovero.get().getStato().equals(Stato.Ricoverato));
        assertNotNull(pazienteRicovero.get().getData_dimissione());
        assertTrue(lettoRicovero.get().getStato());

    }

    @Test
    public void testRicoveraPazienteConRicoveroPassato () {
        //Prendo un paziente ricoverato e termino il suo ricovero, così nello storico rimane il suo ricovero
        Optional<Paziente> paziente = pazienteService.findByCf("DCXNUU76J35I879L");
        PazienteDTO pazienteDTO = new PazienteDTO(paziente.get());
        pazienteController.fineRicovero(pazienteDTO);

        //Aggiungo il paziente al Pronto Soccorso, in modo tale da poterlo ricoverare nuovamente
        pazienteController.addPaziente(pazienteDTO);

        //Ricovero
        pazienteController.ricoveraPaziente(pazienteDTO);

        //Recupero il ricovero appena inserito, verifico che abbia stesso paziente
        Optional<Ricovero> ricovero2 = ricoveroService.findUltimoRicoveroAttivoByPazienteId(pazienteDTO.getId());
        System.out.println(pazienteDTO.getId());
        assertTrue(ricovero2.isPresent());
        assertTrue(paziente.get().getId() == ricovero2.get().getPaziente().getId());

        //Verifico gli Assert
        Letto letto = ricovero2.get().getLetto();
        paziente = pazienteService.findByCf(pazienteDTO.cf);
        assertTrue(letto.getStato());
        assertTrue(paziente.get().getStato().equals(Stato.Ricoverato));
        assertNull(paziente.get().getCodice());
        assertNull(ricovero2.get().getFine_ricovero());
    }

    @Test
    public void testFineRicoveroPazienteNonRicoverato () {
        //Prendo un paziente che non è ricoverato dal Database
        Optional<Paziente> paziente = pazienteService.findByCf("ACXNOF76J35I879K");
        assertFalse(paziente.get().getStato().equals(Stato.Ricoverato));

        //Termino il ricovero
        PazienteDTO pazienteDTO = new PazienteDTO(paziente.get());
        assertThrows(ResponseStatusException.class,
                ()-> pazienteController.fineRicovero(pazienteDTO));

    }

    @Test
    public void testFineRicoveroPazienteInRicovero () {
        //Prendo un paziente ricoverato dal Database
        Optional<Paziente> paziente = pazienteService.findByCf("DCXNUU76J35I879L");
        assertTrue(paziente.get().getStato().equals(Stato.Ricoverato));

        //Termino il suo ricovero
        PazienteDTO pazienteDTO = new PazienteDTO(paziente.get());
        pazienteController.fineRicovero(pazienteDTO);

        //Recupero il ricovero, il letto e il paziente
        paziente = pazienteService.findByCf(pazienteDTO.cf);
        List<Ricovero> ultimiRicoveri = ricoveroService.findUltimoRicoveroNonAttivoByPazienteId(paziente.get().getId(), PageRequest.of(0,1));
        Ricovero ricovero = ultimiRicoveri.get(0);
        assertTrue(ultimiRicoveri.size() == 1);
        Letto letto = ricovero.getLetto();

        //Verifico gli Assert
        assertNotNull(ricovero.getFine_ricovero());
        assertFalse(letto.getStato());
        assertTrue(paziente.get().getStato().equals(Stato.Dimesso));
        assertTrue(paziente.get().getData_dimissione().equals(ricovero.getFine_ricovero()));
    }

    @Test
    public void testGetPazienteInLetto () {
        //Se il letto non è occupato
        Optional<Letto> letto1 = lettoRepository.findFirstByStatoFalse();
        assertThrows(ResponseStatusException.class,
                ()-> pazienteController.getPazienteInLetto(letto1.get().getId()));

        //Se il letto è occupato e verifico gli assert
        Optional<Letto> letto2 = lettoRepository.findFirstOccupatoConRicoveroAssociatoInCorso();
        assertTrue(letto2.isPresent());
        Optional<Ricovero> ricovero = ricoveroService.findRicoveroNonTerminatoByIdLetto(letto2.get().getId());
        assertTrue(ricovero.isPresent());
        Paziente paziente = ricovero.get().getPaziente();
        assertTrue(pazienteService.checkMandatoryArguments(paziente));
    }

    public PazienteDTO getPazienteDTOFromBody () {
        //Simulazione PazienteDTO provienente dal Body della richiesta
        PazienteDTO pazienteDTO = new PazienteDTO();
        pazienteDTO.setCf("APPNOF76J35I879K");
        pazienteDTO.setNome("Vincenzo");
        pazienteDTO.setCognome("Prova");
        pazienteDTO.setNazionalita("Italiana");
        pazienteDTO.setData_nascita(Instant.now());
        pazienteDTO.setCodice(Codice.GIALLO);
        pazienteDTO.setLuogo_nascita("Bari");
        pazienteDTO.setSesso(Sesso.M);
        pazienteDTO.setDiagnosi("Frattura braccio");
        pazienteDTO.setData_entrata(Instant.now());
        pazienteDTO.setData_dimissione(null);
        pazienteDTO.setDiagnosi(null);
        return pazienteDTO;
    }

    public PazienteDTO getPazienteDTODiProva (String cf) {
        //Simulazione PazienteDTO provienente dal Body della richiesta con Codice Fiscale customizzabile
        PazienteDTO pazienteDTO = new PazienteDTO();
        pazienteDTO.setCf(cf);
        pazienteDTO.setNome("Vincenzo");
        pazienteDTO.setCognome("Prova");
        pazienteDTO.setNazionalita("Italiana");
        pazienteDTO.setData_nascita(Instant.now());
        pazienteDTO.setCodice(Codice.GIALLO);
        pazienteDTO.setLuogo_nascita("Bari");
        pazienteDTO.setSesso(Sesso.M);
        pazienteDTO.setDiagnosi("Frattura braccio");
        pazienteDTO.setData_entrata(Instant.now());
        pazienteDTO.setData_dimissione(null);
        pazienteDTO.setDiagnosi(null);
        return pazienteDTO;
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
