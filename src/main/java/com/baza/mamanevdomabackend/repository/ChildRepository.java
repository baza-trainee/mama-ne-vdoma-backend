package com.baza.mamanevdomabackend.repository;

import com.baza.mamanevdomabackend.entity.Child;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildRepository extends CrudRepository<Child,Long> {
}
