package com.baza.mamanevdomabackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChildNotFoundException extends RuntimeException {
    public ChildNotFoundException(String message) {
        super(message);
    }
}
