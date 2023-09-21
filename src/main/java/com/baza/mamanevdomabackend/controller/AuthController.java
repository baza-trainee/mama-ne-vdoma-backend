package com.baza.mamanevdomabackend.controller;

import com.baza.mamanevdomabackend.payload.request.LoginRequest;
import com.baza.mamanevdomabackend.payload.request.RegisterRequest;
import com.baza.mamanevdomabackend.payload.response.MessageResponse;
import com.baza.mamanevdomabackend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping({"/login", "sign-in"})
    public ResponseEntity<MessageResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping(value = "/confirm-account")
    public ResponseEntity<MessageResponse> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        return ResponseEntity.ok(authService.confirmEmail(confirmationToken));
    }
}
