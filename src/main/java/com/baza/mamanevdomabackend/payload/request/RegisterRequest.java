package com.baza.mamanevdomabackend.payload.request;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String email;
    private String username;
    private String password;
}
