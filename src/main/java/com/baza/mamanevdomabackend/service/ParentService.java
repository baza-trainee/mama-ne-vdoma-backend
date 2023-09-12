package com.baza.mamanevdomabackend.service;

import com.baza.mamanevdomabackend.entity.Parent;
import com.baza.mamanevdomabackend.exception.ParentNotFoundException;
import com.baza.mamanevdomabackend.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParentService {

    @Autowired
    private ParentRepository parentRepository;

    public Parent findParentByEmail(String email) {
        return parentRepository.findByEmail(email)
                .orElseThrow(() -> new ParentNotFoundException("Parent not found with this email: " + email));
    }

    public Parent findParentByUsername(String username) {
        return parentRepository.findByUsername(username)
                .orElseThrow(() -> new ParentNotFoundException("Parent not found with this username: " + username));
    }
}
