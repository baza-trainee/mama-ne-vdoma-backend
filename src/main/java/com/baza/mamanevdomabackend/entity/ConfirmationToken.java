package com.baza.mamanevdomabackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String confirmationToken;

    @Column
    private Date createdDate;

    @OneToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Parent parent;
}