package com.baza.mamanevdomabackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.List;

@Entity(name = "parents")
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    @Email
    private String email;
    private String password;
    private Long likes;
    private Long dislikes;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child> children;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<CareSchedule> schedules;

    @OneToMany(mappedBy = "owner")
    private List<ChildrenGroup> groups;
}
