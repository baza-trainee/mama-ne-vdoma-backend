package com.baza.mamanevdomabackend.entity;

import jakarta.persistence.*;

@Entity(name = "children")
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int age;
    private String name;
    @ManyToOne
    private Parent parent;
    @ManyToOne
    private ChildrenGroup group;
}
