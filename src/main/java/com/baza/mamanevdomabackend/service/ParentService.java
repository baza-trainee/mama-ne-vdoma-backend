package com.baza.mamanevdomabackend.service;

import com.baza.mamanevdomabackend.entity.Parent;
import com.baza.mamanevdomabackend.exception.UserExistException;
import com.baza.mamanevdomabackend.exception.ParentNotFoundException;
import com.baza.mamanevdomabackend.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

@Service
public class ParentService {

    @Autowired
    private ParentRepository parentRepository;

    public Parent create(Parent parent) {
        Optional<Parent> optionalParentCreate = parentRepository.findByEmail(parent.getEmail());
        if (optionalParentCreate.isEmpty()) {
            return parentRepository.save(parent);
        }

        throw new UserExistException("Parent with email " + parent.getEmail()
                + "is already created");
    }

    public Parent findParentByEmail(String email) {
        return parentRepository.findByEmail(email)
                .orElseThrow(() -> new ParentNotFoundException("Parent not found with this email: " + email));
    }

    public Parent findParentByNickname(String username) {
        return parentRepository.findByNickname(username)
                .orElseThrow(() -> new ParentNotFoundException("Parent not found with this username: " + username));
    }

    public boolean existsByEmail(String email) {
        return parentRepository.existsByEmail(email);
    }

    @Transactional
    public void updateIsEnabledByParentEmail(Boolean isEnabled, String email) {
        parentRepository.updateIsEnabledByParentEmail(isEnabled, email);
    }


    public Parent getCurrentParent(Principal principal) {
        String email = principal.getName();
        return findParentByEmail(email);
    }
}
