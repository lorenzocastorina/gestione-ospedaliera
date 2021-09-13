package com.ospedale.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoContentException extends ResponseStatusException {

    public NoContentException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
