package com.ospedale.project.controller;

import com.ospedale.project.dto.LoginDTO;
import com.ospedale.project.dto.OperatoreDTO;
import com.ospedale.project.model.Operatore;
import com.ospedale.project.repository.OperatoreRepository;
import com.ospedale.project.service.OperatoreService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OperatoreControllerTest {

    @Autowired
    OperatoreController operatoreController;
    @Autowired
    OperatoreService operatoreService;
    @Autowired
    OperatoreRepository operatoreRepository;

    @BeforeEach
    public void init() {
        inizializeDb();
    }

    @AfterEach
    public void destroy () {deleteDb();}

    @Test
    public void testLogin () {
        //Creo il LoginDTO e effettuo il login
        String username = "admin";
        String password = "admin";
        LoginDTO loginDTO = new LoginDTO(username, password);
        OperatoreDTO operatoreDTO = operatoreController.login(loginDTO);
        List<String> operatoriLoggati = OperatoreService.getOperatoriLoggati();

        //Verifico gli Assert
        assertTrue(operatoreDTO.username.equals(username));
        assertTrue(operatoreService.isOperatoreLogged(username));
        assertTrue(operatoriLoggati.contains(username));
    }

    @Test
    public void testLoginFail () {
        //Creo il LoginDTO e effettuo il login
        String username = "fail";
        String password = "fail";
        LoginDTO loginDTO = new LoginDTO(username, password);
        operatoreController.login(loginDTO);
        List<String> operatoriLoggati = OperatoreService.getOperatoriLoggati();

        //Verifico gli Assert
        assertFalse(operatoreService.isOperatoreLogged(username));
        assertFalse(operatoriLoggati.contains(username));
    }

    @Test
    public void testIsLogged () {
        //Testo che l'operatore sia loggato correttamente
        String username = "admin";
        LoginDTO loginDTO = new LoginDTO(username, "admin");
        operatoreController.login(loginDTO);
        List<String> operatoriLoggati = OperatoreService.getOperatoriLoggati();

        //Verifico gli Assert
        assertTrue(operatoreController.isLogged(username));
        assertTrue(operatoriLoggati.contains(username));
    }

    @Test
    public void testIsLoggedFail () {
        //Testo che l'operatore sia loggato correttamente
        String username = "fail";
        LoginDTO loginDTO = new LoginDTO(username, "fail");
        operatoreController.login(loginDTO);
        List<String> operatoriLoggati = OperatoreService.getOperatoriLoggati();

        //Verifico gli Assert
        assertFalse(operatoreController.isLogged(username));
        assertFalse(operatoriLoggati.contains(username));
    }

    @Test
    public void testLogout () {
        //Testo che l'operatore esegua il logout correttamente
        String username = "admin";
        LoginDTO loginDTO = new LoginDTO(username, "admin");
        operatoreController.login(loginDTO);
        operatoreController.logout(username);
        List<String> operatoriLoggati = OperatoreService.getOperatoriLoggati();

        //Verifico gli Assert
        assertFalse(operatoriLoggati.contains(username));
    }

    public void inizializeDb() {
        //Creazione operatore
        Operatore operatore = new Operatore("admin", "admin");

        //Aggiunta al Database
        operatoreRepository.save(operatore);
    }

    public void deleteDb() {
        //Elimino tutti gli elementi dal Database
        operatoreRepository.deleteAll();
    }

}
