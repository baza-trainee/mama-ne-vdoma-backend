package com.baza.mamanevdomabackend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ChildrenGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ManyToOne
    private Parent owner;
    @OneToMany(mappedBy = "group")
    private List<Child> children;
}
