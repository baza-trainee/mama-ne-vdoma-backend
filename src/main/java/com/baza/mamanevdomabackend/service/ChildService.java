package com.baza.mamanevdomabackend.service;

import com.baza.mamanevdomabackend.entity.Child;
import com.baza.mamanevdomabackend.exception.ChildNotFoundException;
import com.baza.mamanevdomabackend.repository.ChildRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChildService {

    private final ChildRepository childRepository;

    public Child findChildById(Long childId) {
        return childRepository.findById(childId)
                .orElseThrow(() -> new ChildNotFoundException("Child not exists"));
    }
}
