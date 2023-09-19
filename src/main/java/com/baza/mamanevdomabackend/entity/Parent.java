package com.baza.mamanevdomabackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "parents")
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Email(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@\" + \"[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$")
    @Column(unique = true)
    private String email;
    private String password;
    private Long likes;
    private Long dislikes;
    private boolean isEnabled;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child> children;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<CareSchedule> schedules;
    @OneToMany(mappedBy = "owner")
    private List<ChildrenGroup> groups;
    @ManyToOne
    private Location location;
}
