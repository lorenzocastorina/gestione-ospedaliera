package com.ospedale.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoMandatoryArgumentException extends ResponseStatusException {

    public NoMandatoryArgumentException(HttpStatus status) {
        super(status, "Mancano dei campi obbligatori");
    }
}
