package com.baza.mamanevdomabackend.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/secured")
    public String getSecured(@AuthenticationPrincipal OAuth2User oAuth2User) {
        System.out.println(oAuth2User);

        return "Hello secured";
    }
}
