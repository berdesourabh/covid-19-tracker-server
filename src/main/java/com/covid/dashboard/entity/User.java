    package com.covid.dashboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

    @Entity
@Table(name = "user")
@Getter
@Setter
public class User {

    @Id
    @Column
    private String email;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(length = 60)
    private String password;

    @Column
    private String country;

    @Column
    private String state;

    @Column
    private String city;

    @Column
    private String role;

    @Column
    private String verificationCode;

    @Column
    private boolean enabled;

    @JsonIgnore
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,targetEntity = Patient.class)
    private Patient patient;
}
