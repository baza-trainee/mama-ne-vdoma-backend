package com.baza.mamanevdomabackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ParentNotFoundException extends RuntimeException{
    public ParentNotFoundException(String msg) {
        super(msg);
    }
}
