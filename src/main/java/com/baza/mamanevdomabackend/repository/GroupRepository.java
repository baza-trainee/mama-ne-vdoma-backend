package com.baza.mamanevdomabackend.repository;

import com.baza.mamanevdomabackend.entity.ChildrenGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<ChildrenGroup, Long> {
}
