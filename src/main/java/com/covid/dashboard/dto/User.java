package com.covid.dashboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {


    private String email;

    private String firstName;

    private String lastName;

    private String country;

    private String city;

    private String state;

    private String password;

    private String verificationCode;

    private Patient patient;

}
