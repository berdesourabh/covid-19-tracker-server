package com.covid.dashboard.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
