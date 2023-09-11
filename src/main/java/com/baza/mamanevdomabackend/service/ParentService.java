package com.baza.mamanevdomabackend.service;

import com.baza.mamanevdomabackend.entity.Parent;
import com.baza.mamanevdomabackend.exception.ParentNotFoundException;
import com.baza.mamanevdomabackend.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParentService {

    @Autowired
    private ParentRepository parentRepository;

    public Parent findParentByLoginOrEmail(String email) {
        Optional<Parent> parent = parentRepository.findByEmail(email);
        if (parent.isEmpty()) {
            return parentRepository.findByLogin(email)
                    .orElseThrow(() -> new ParentNotFoundException("Parent not found with this email: " + email));
        } else {
            return parent.get();
        }
    }
}
