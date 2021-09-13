package com.ospedale.project.controller;

import com.ospedale.project.dto.ScambioDocumentiDTO;
import com.ospedale.project.exceptions.NoContentException;
import com.ospedale.project.model.Documento;
import com.ospedale.project.model.Paziente;
import com.ospedale.project.service.PazienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

@CrossOrigin(origins = "*") //Per fare le richieste sulla stessa rete del frontend, "*" significa tutte
@RestController
@RequestMapping("/documento")
public class DocumentoController {

    @Autowired
    PazienteService pazienteService;

    @Transactional
    @PostMapping("/aggiungi")
    public Boolean addDocumento (@RequestBody ScambioDocumentiDTO scambioDocumentiDTO) {

        //Se il paziente non è presente all'interno della richiesta
        if (scambioDocumentiDTO.getPazienteDTO() == null){
            throw new NoContentException(HttpStatus.NO_CONTENT, "Il paziente non è presente all'interno della richiesta. Riprovare");
        }

        //Se il documento non è presente all'interno della richiesta
        else if (scambioDocumentiDTO.getNome() == null ) {
            throw new NoContentException(HttpStatus.NO_CONTENT, "Il nome del documento non è presente all'interno della richiesta. Riprovare");
        }

        //Altrimenti aggiunge il documento al paziente passato nel body della richiesta
        Optional<Paziente> paziente = pazienteService.findByCf(scambioDocumentiDTO.getPazienteDTO().getCf());
        Documento documento = new Documento(scambioDocumentiDTO.getNome());
        paziente.get().getDocumenti().add(documento);
        pazienteService.save(paziente.get());
        return true;
    }
}
