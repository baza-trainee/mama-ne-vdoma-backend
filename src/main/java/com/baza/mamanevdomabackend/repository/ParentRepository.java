package com.baza.mamanevdomabackend.repository;

import com.baza.mamanevdomabackend.entity.Parent;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends CrudRepository<Parent, Long> {
    Optional<Parent> findByEmail(String email);

    Optional<Parent> findByUsername(String username);

    Boolean existsByEmail(String email);

    @Modifying
    @Query("update parents p set p.isEnabled = ?1 where p.email = ?2")
    void updateIsEnabledByParentEmail(Boolean enable, String email);
}
