package com.ospedale.project.controller;

import com.ospedale.project.dto.PazienteDTO;
import com.ospedale.project.dto.ScambioLettoDTO;
import com.ospedale.project.enumPackage.Stato;
import com.ospedale.project.exceptions.LettoStatusException;
import com.ospedale.project.exceptions.NoMandatoryArgumentException;
import com.ospedale.project.exceptions.PazienteNonPresenteException;
import com.ospedale.project.exceptions.PazienteStatusException;
import com.ospedale.project.model.Letto;
import com.ospedale.project.model.Paziente;
import com.ospedale.project.model.Ricovero;
import com.ospedale.project.service.LettoService;
import com.ospedale.project.service.PazienteService;
import com.ospedale.project.service.RicoveroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*") //Per fare le richieste sulla stessa rete del frontend, "*" significa tutte
@RestController
@RequestMapping("/paziente")
public class PazienteController {

    @Autowired
    PazienteService pazienteService;
    @Autowired
    RicoveroService ricoveroService;
    @Autowired
    LettoService lettoService;

    @GetMapping("/getPazienti")
    public List<PazienteDTO> getPazienti () {
        //Ritorno una lista con tutti i pazienti
        return pazienteService.findAll().stream().map(PazienteDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/soccorso")
    public List<PazienteDTO> getPazientiProntoSoccorso () {
        //Prendo tutti i pazienti che si trovano nel Pronto Soccorso
        List<Paziente> pazienti = pazienteService.findAllProntoSoccorso();

        //Ordino in base al codice e in base alla data (a parità di codice).
        pazienti.sort(Comparator.comparing((Paziente p) -> (p.getCodice())).thenComparing(p -> p.getData_entrata()));
        return pazienti.stream().map(PazienteDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/ricovero")
    public List<PazienteDTO> getPazientiRicoverati () {
        //Prendo tutti i pazienti ricoverati
        List<Paziente> pazienti = pazienteService.findAllRicoverati();
        return pazienti.stream().map(PazienteDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/dimessi")
    public List<PazienteDTO> getPazientiDimessi () {
        //Prendo tutti i pazienti dimessi
        List<Paziente> pazienti = pazienteService.findAllDimessi();
        return pazienti.stream().map(PazienteDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PazienteDTO getPaziente (@PathVariable("id") Long id) {
        //Prendo il paziente in base all'id
        Optional<Paziente> paziente = pazienteService.findById(id);
        return paziente.map(PazienteDTO::new).orElse(null);
    }

    @Transactional
    @PostMapping("/aggiungi")
    public Boolean addPaziente (@RequestBody PazienteDTO pazienteDTO) {
        Paziente paziente = new Paziente(pazienteDTO);

        //Controllo se ci sono tutti i dati obbligatori
        if(!pazienteService.checkMandatoryArguments(paziente)) {
            throw new NoMandatoryArgumentException(HttpStatus.BAD_REQUEST);
        }

        //Controllo se il paziente è gia stato dimesso in passato
        if(pazienteService.checkIfPazienteIsInLista(paziente.getCf(), pazienteService.findAllDimessi())) {
            Optional<Paziente> pazienteEsistente = pazienteService.findByCf(paziente.getCf());
            pazienteService.aggiornaPaziente(pazienteEsistente.get(), pazienteDTO);
            pazienteEsistente.get().setData_entrata(Instant.now());
            pazienteEsistente.get().setData_dimissione(null);
            pazienteEsistente.get().setStato(Stato.ProntoSoccorso);
            pazienteService.save(pazienteEsistente.get()); //aggiorno il paziente
        }

        //Controllo se il paziente fa parte di quelli attualmente nel pronto soccorso
        else if(pazienteService.checkIfPazienteIsInLista(paziente.getCf(), pazienteService.findAllProntoSoccorso())) {
            throw new PazienteStatusException(HttpStatus.BAD_REQUEST, "Il paziente inserito è già presente nel pronto soccorso.");
        }

        //Controllo se il paziente fa parte di quelli ricoverati
        else if(pazienteService.checkIfPazienteIsInLista(paziente.getCf(), pazienteService.findAllRicoverati())) {
            throw new PazienteStatusException(HttpStatus.BAD_REQUEST, "Il paziente inserito è attualmente ricoverato.");
        }

        //Controllo se il paziente è stato eliminato
        else if (pazienteService.checkIfPazienteIsInLista(paziente.getCf(), pazienteService.findAllEliminati())) {
            Optional<Paziente> pazienteEsistente = pazienteService.findEliminatiByCf(paziente.getCf());
            //Aggiorno i suoi dati
            pazienteService.aggiornaPaziente(pazienteEsistente.get(), pazienteDTO);
            paziente.setDocumenti(Collections.EMPTY_LIST);
            pazienteEsistente.get().setActive(true);
            pazienteEsistente.get().setData_entrata(Instant.now());
            pazienteEsistente.get().setData_dimissione(null);
            pazienteEsistente.get().setStato(Stato.ProntoSoccorso);
            pazienteService.save(pazienteEsistente.get());
        }

        //Se è un nuovo paziente lo aggiungo nel pronto soccorso.
        else {
            pazienteService.save(paziente);
        }
        return true;
    }

    @Transactional
    @PostMapping("/rimuovi")
    public Boolean eliminaPaziente (@RequestBody PazienteDTO pazienteDTO) {
        Optional<Paziente> paziente = pazienteService.findByCf(pazienteDTO.getCf());

        //Controllo se il paziente non è presente
        if(!paziente.isPresent()) {
            throw new PazienteNonPresenteException(HttpStatus.BAD_REQUEST,"Paziente non presente nel sistema.");
        }

        //Il paziente può essere eliminato solo se è stato dimesso
        else if(paziente.get().getStato() == Stato.Dimesso) {
            pazienteService.delete(paziente);
            return true;
        }

        //Se non è stato dimesso
        else {
            throw new PazienteStatusException(HttpStatus.BAD_REQUEST, "Il paziente non è stato ancora dimesso. Impossibile eliminarlo.");
        }
    }

    @Transactional
    @PostMapping("/dimettiSoccorso") //Funzione che dimette il paziente direttamente dal Pronto soccorso.
    public Boolean dimettiPaziente (@RequestBody PazienteDTO pazienteDTO) {
        Optional<Paziente> paziente = pazienteService.findByCf(pazienteDTO.getCf());

        //Se il paziente è nel pronto soccorso lo posso dimettere (senza passare dal ricovero).
        if (pazienteService.checkIfPazienteIsInLista(paziente.get().getCf(), pazienteService.findAllProntoSoccorso())) {
            paziente.get().setData_dimissione(Instant.now());
            paziente.get().setStato(Stato.Dimesso); //dimetto il paziente
            return true;
        }

        //Altrimenti lancio una PazienteStatusException
        else {
            throw new PazienteStatusException(HttpStatus.BAD_REQUEST, "Il paziente non è nel Pronto Soccorso");
        }
    }

    @Transactional //Questa annotazione è utile quando eseguo più operazioni nel database
    @PostMapping("/ricovera")
    public Boolean ricoveraPaziente (@RequestBody PazienteDTO pazienteDTO) {
        //Controllo se i letti sono tutti occupati
        if (lettoService.findAllLiberi().isEmpty()) {
            throw new LettoStatusException(HttpStatus.BAD_REQUEST, "I letti sono tutti occupati. Impossibile ricoverare il paziente.");
        }
        Optional<Paziente> paziente = pazienteService.findByCf(pazienteDTO.cf);
        Optional<Letto> letto = lettoService.findFirstByStatoFalse(); //prendo il primo letto libero
        List<Ricovero> ricoveriEsistenti = ricoveroService.findUltimoRicoveroNonAttivoByPazienteId(paziente.get().getId(), PageRequest.of(0,1));

        //Controllo se il paziente era già stato ricoverato in passato e ora è dimesso.
        if (!ricoveriEsistenti.isEmpty()) {
            Ricovero ricoveroEsistente = ricoveriEsistenti.get(0);
            ricoveroEsistente.setFine_ricovero(null);
            ricoveroEsistente.setInizio_ricovero(Instant.now());
            ricoveroEsistente.setLetto(letto.get());
            ricoveroService.save(ricoveroEsistente);
            letto.get().setStato(true);
            lettoService.save(letto.get()); //aggiorno che il letto è occupato
            paziente.get().setStato(Stato.Ricoverato); //aggiorno lo stato del paziente a ricoverato
            paziente.get().setCodice(null);
            pazienteService.save(paziente.get()); //aggiorno che il paziente è stato dimesso dal pronto soccorso
            return true;
        }

        //Se il paziente è in pronto soccorso lo ricovero
        else if (paziente.get().getStato() == Stato.ProntoSoccorso) {
            Ricovero ricovero = new Ricovero(letto.get(), paziente.get());
            ricoveroService.save(ricovero); //salvo il nuovo ricovero
            letto.get().setStato(true);
            lettoService.save(letto.get()); //aggiorno che il letto è occupato
            paziente.get().setData_dimissione(Instant.now());
            paziente.get().setStato(Stato.Ricoverato); //aggiorno lo stato del paziente a ricoverato
            paziente.get().setCodice(null);
            pazienteService.save(paziente.get()); //aggiorno che il paziente è stato dimesso dal pronto soccorso
            return true;
        }

        //Altrimenti lancio una ResponseStatusException
        else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Qualcosa è andato storto. Impossibile ricoverare il paziente.");
        }
    }

    @Transactional
    @PostMapping("/dimettiRicovero")
    public Boolean fineRicovero (@RequestBody PazienteDTO pazienteDTO) {
        //Il paziente non è ricoverato
        Optional<Paziente> paziente = pazienteService.findByCf(pazienteDTO.cf);
        if (!(paziente.get().getStato().equals(Stato.Ricoverato))) {
            throw new PazienteStatusException(HttpStatus.BAD_REQUEST, "In questo momento il paziente non è ricoverato");
        }

        //Il paziente è ricoverato
        Optional<Ricovero> ricovero = ricoveroService.findUltimoRicoveroAttivoByPazienteId(pazienteDTO.getId());
        Optional<Letto> letto = lettoService.findById(ricovero.get().getLetto().getId());
        if (ricovero.isPresent() && letto.isPresent()) {
            ricovero.get().setFine_ricovero(Instant.now()); //termino il ricovero
            letto.get().setStato(false); //libero il letto
            paziente.get().setStato(Stato.Dimesso); //dimetto il paziente
            paziente.get().setData_dimissione(ricovero.get().getFine_ricovero()); //metto la data dimissione uguale alla data di fine ricovero
            pazienteService.save(paziente.get()); //salvo le modifiche sul paziente
            lettoService.save(letto.get()); //salvo l'update
            ricoveroService.save(ricovero.get()); //salvo le modifiche apportate al ricovero
            return true;
        }

        //Altrimenti lancio una ResponseStatusException
        else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Impossibile terminare il ricovero del paziente selezionato.");
        }
    }

    @Transactional
    @PostMapping("/cambiaLetto")
    public Boolean cambiaLetto (@RequestBody ScambioLettoDTO scambioLettoDTO) {
        //Prendo il ricovero del paziente, l'id del vecchio letto e l'id del letto nuovo
        Optional<Ricovero> ricovero = ricoveroService.findRicoveroNonTerminatoByIdLetto(scambioLettoDTO.getIdLettoVecchio());
        Optional<Letto> lettoVecchio = lettoService.findById(ricovero.get().getLetto().getId());
        Optional<Letto> lettoNuovo = lettoService.findById(scambioLettoDTO.getIdLettoNuovo());

        //Se sono tutti presenti
        if (ricovero.isPresent() && lettoNuovo.isPresent() && lettoVecchio.isPresent()) {
            lettoVecchio.get().setStato(false); //libero il vecchio letto
            lettoService.save(lettoVecchio.get());
            lettoNuovo.get().setStato(true); //occupo il nuovo letto
            lettoService.save(lettoNuovo.get());
            ricovero.get().setLetto(lettoNuovo.get()); //assegno il nuovo letto al paziente
            ricoveroService.save(ricovero.get()); //salvo le modifiche apportate al ricovero
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @GetMapping("/letto/{idLetto}")
    public PazienteDTO getPazienteInLetto (@PathVariable("idLetto") Long id) {
        //Controllo se il letto è vuoto
        if (!lettoService.findById(id).get().getStato()) {
            throw new LettoStatusException(HttpStatus.BAD_REQUEST, "Il letto non è occupato da un paziente");
        }

        //Se non è vuoto ritorno il paziente che è associato a quel ricovero
        Optional<Ricovero> ricovero = ricoveroService.findRicoveroNonTerminatoByIdLetto(id);
        if (ricovero.isPresent()) {
            Paziente paziente = ricovero.get().getPaziente();
            paziente.setData_entrata(ricovero.get().getInizio_ricovero());
            PazienteDTO pazienteDTO = new PazienteDTO(paziente);
            return pazienteDTO;
        } else {
            return null;
        }

    }
}
