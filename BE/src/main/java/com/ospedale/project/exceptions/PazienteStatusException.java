package com.ospedale.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PazienteStatusException extends ResponseStatusException {

    public PazienteStatusException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
