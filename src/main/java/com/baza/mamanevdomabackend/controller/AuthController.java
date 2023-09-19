package com.baza.mamanevdomabackend.controller;

import com.baza.mamanevdomabackend.dto.LoginDto;
import com.baza.mamanevdomabackend.dto.RegisterDto;
import com.baza.mamanevdomabackend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping({"/register", "/sign-up"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        return new ResponseEntity<>(authService.register(registerDto), HttpStatus.OK);
    }

    @PostMapping({"/login", "sign-in"})
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
    }

    @PostMapping(value = "/confirm-account")
    public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        return new ResponseEntity<>(authService.confirmEmail(confirmationToken), HttpStatus.OK);
    }
}
