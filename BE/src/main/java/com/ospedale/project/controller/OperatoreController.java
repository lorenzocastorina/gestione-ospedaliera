package com.ospedale.project.controller;

import com.ospedale.project.dto.LoginDTO;
import com.ospedale.project.dto.OperatoreDTO;
import com.ospedale.project.service.OperatoreService;
import com.ospedale.project.model.Operatore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*") //Per fare le richieste sulla stessa rete del frontend, "*" significa tutte
@RestController
@RequestMapping("/operatore")
public class OperatoreController {

    @Autowired
    OperatoreService operatoreService;

    @Transactional
    @PostMapping("/login")
    public OperatoreDTO login (@RequestBody LoginDTO loginCredentials) {
        //Cerco l'operatore nel Database
        Optional<Operatore> operatore = operatoreService.findByUsernameAndPassword(loginCredentials.username, loginCredentials.password);

        //Se è presente lo aggiungo alla lista degli operatori loggati
        operatore.ifPresent(o -> operatoreService.logOperatore(o.getUsername()));
        return operatore.map(OperatoreDTO::new).orElse(null);
    }

    @GetMapping("/isLogged/{username}")
    public Boolean isLogged(@PathVariable("username") String username) {
        //Controllo se l'operatore è loggato
        List<String> operatoriLoggati = OperatoreService.getOperatoriLoggati();
        System.out.println(operatoriLoggati);
        return operatoreService.isOperatoreLogged(username);
    }

    @Transactional
    @PostMapping("/logout")
    public Boolean logout (@RequestBody String username) {
        //Se l'operatore è loggato, effettuo il logout
            if (operatoreService.isOperatoreLogged(username)) {
                operatoreService.logOutOperatore(username);
                return true;
            } else {
                return false;
            }
        }
    }
