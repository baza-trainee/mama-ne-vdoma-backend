package com.baza.mamanevdomabackend.repository;

import com.baza.mamanevdomabackend.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

    Optional<Parent> findByEmail(String email);

    Optional<Parent> findByLogin(String login);

}
