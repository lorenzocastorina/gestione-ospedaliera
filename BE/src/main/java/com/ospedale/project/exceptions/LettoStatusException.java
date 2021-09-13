package com.ospedale.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LettoStatusException extends ResponseStatusException {

    public LettoStatusException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
