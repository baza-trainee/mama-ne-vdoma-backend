package com.baza.mamanevdomabackend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private String city;
    private String address;
    private Integer radius;
    @OneToMany(mappedBy = "location")
    private List<Parent> parents;
    private Double latitude;
    private Double longitude;
}
