package com.baza.mamanevdomabackend.service;

import com.baza.mamanevdomabackend.entity.Parent;
import com.baza.mamanevdomabackend.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ParentService parentService;
    private final ParentRepository parentRepository;

    @Autowired
    public CustomUserDetailsService(ParentService parentService, ParentRepository parentRepository) {
        this.parentService = parentService;
        this.parentRepository = parentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return parentService.findParentByEmail(email);
    }


    public Parent loadParentById(Long id) {
        return parentRepository.findById(id).orElse(null);
    }
}
