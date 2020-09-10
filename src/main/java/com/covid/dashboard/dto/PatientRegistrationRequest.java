package com.covid.dashboard.dto;

import lombok.Data;

@Data
public class PatientRegistrationRequest {

    private String email;

    private String firstName;

    private String lastName;

    private String country;

    private String city;

    private String state;

    private String password;

    private String verificationCode;

    private String coronaPositive;

    private String symptoms;

    private boolean dead;

    private String physicianId;

}
