package com.baza.mamanevdomabackend.repository;

import com.baza.mamanevdomabackend.entity.Parent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends CrudRepository<Parent, Long> {

    Optional<Parent> findByEmail(String email);

    Optional<Parent> findByUsername(String username);

}
