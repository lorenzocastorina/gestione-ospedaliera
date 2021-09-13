package com.ospedale.project.service;

import com.ospedale.project.model.Operatore;
import com.ospedale.project.repository.OperatoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OperatoreService {

    @Autowired
    OperatoreRepository operatoreRepository;

    private static List<String> operatoriLoggati = new ArrayList<>();

    public Optional<Operatore> findByUsernameAndPassword(String username, String password) {
        return operatoreRepository.findByUsernameAndPassword(username, password);
    }

    public static List<String> getOperatoriLoggati() {
        return operatoriLoggati;
    }

    public static void setOperatoriLoggati(List<String> operatoriLoggati) {
        OperatoreService.operatoriLoggati = operatoriLoggati;
    }

    public void logOperatore(String username){
        if(!isOperatoreLogged(username))
            operatoriLoggati.add(username);
    }
    public boolean isOperatoreLogged(String username){
        return operatoriLoggati.contains(username);
    }

    public void logOutOperatore(String username){
        operatoriLoggati.remove(username);
    }
}
