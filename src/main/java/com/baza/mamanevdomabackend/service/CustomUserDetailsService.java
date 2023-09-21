package com.baza.mamanevdomabackend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ParentService parentService;

    public CustomUserDetailsService(ParentService parentService) {
        this.parentService = parentService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return parentService.findParentByEmail(email);
    }
}
