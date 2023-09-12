package com.baza.mamanevdomabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {
    @GetMapping("/")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/secured")
    public String getSecured(@AuthenticationPrincipal OAuth2User principal) {

        for (Map.Entry<String, Object> objectEntry :
                principal.getAttributes().entrySet()) {
            System.out.println("key -> " + objectEntry.getKey() + " value -> " + objectEntry.getValue());
        }

        return "Hello secured";
    }
}
