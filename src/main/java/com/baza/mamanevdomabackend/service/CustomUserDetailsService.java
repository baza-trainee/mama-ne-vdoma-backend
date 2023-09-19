package com.baza.mamanevdomabackend.service;

import com.baza.mamanevdomabackend.entity.Parent;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final  ParentService parentService;

    public CustomUserDetailsService(ParentService parentService) {
        this.parentService = parentService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Parent parent = parentService.findParentByEmail(email);
        return new User(parent.getEmail(), parent.getPassword(), new HashSet<>());
    }
}
