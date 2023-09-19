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

    public Parent update(Parent parent){
        Optional<Parent> optionalParentUpdate = parentRepository.findByEmail(parent.getEmail());
        if(optionalParentUpdate.isPresent()){
            Parent target = Parent.builder()
                    .id(parent.getId())
                    .username(parent.getUsername())
                    .likes(parent.getLikes())
                    .dislikes(parent.getDislikes())
                    .email(parent.getEmail())
                    .password(parent.getPassword())
                    .schedules(parent.getSchedules())
                    .groups(parent.getGroups())
                    .location(parent.getLocation())
                    .children(parent.getChildren())
                    .isEnabled(parent.isEnabled())
                    .build();

            return parentRepository.save(target);
        }

        throw new IllegalArgumentException("Unable to update user");
    }
    public Parent create(Parent parent) {
        Optional<Parent> optionalParentCreate = parentRepository.findByEmail(parent.getEmail());
        if (optionalParentCreate.isEmpty()) {
            return parentRepository.save(parent);
        }

        throw new IllegalArgumentException("Parent with email " + parent.getEmail()
                + "is already created");
    }

    public Parent findParentByEmail(String email) {
        return parentRepository.findByEmail(email)
                .orElseThrow(() -> new ParentNotFoundException("Parent not found with this email: " + email));
    }

    public Parent findParentByUsername(String username) {
        return parentRepository.findByUsername(username)
                .orElseThrow(() -> new ParentNotFoundException("Parent not found with this username: " + username));
    }

    public boolean existsByEmail(String email) {
        return parentRepository.existsByEmail(email);
    }
}
