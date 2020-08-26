package com.covid.dashboard.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue
    @Column
    private int patientId;

    @OneToOne
    @JoinColumn(name = "email")
    private User user;

    @Column
    private String symptoms;

    @Column
    @JsonProperty
    private boolean isCoronaPositive;


}
