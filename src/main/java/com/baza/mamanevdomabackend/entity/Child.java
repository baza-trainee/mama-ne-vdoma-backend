package com.baza.mamanevdomabackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity(name = "children")
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Age cannot be empty")
    private int age;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @ManyToOne
    private Parent parent;
    @ManyToOne
    private ChildrenGroup group;
}
