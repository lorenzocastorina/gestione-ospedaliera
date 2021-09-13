package com.ospedale.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PazienteNonPresenteException extends ResponseStatusException {

    public PazienteNonPresenteException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
