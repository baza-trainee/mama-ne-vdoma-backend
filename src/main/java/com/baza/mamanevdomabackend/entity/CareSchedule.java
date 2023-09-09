package com.baza.mamanevdomabackend.entity;

import com.baza.mamanevdomabackend.entity.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "schedule")
public class CareSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Status status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @ManyToOne
    private Parent parent;
}
