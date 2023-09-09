package com.baza.mamanevdomabackend.entity;

import jakarta.persistence.*;

@Entity(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private String city;
    private String address;
    private Integer radius;
    @OneToOne
    private Parent parent;
    private Double latitude;
    private Double longitude;
}
