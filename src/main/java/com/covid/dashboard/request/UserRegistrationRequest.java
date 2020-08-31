package com.covid.dashboard.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequest {

    private String email;
    private String firstName;
    private String lastName;
    private String country;
    private String state;
    private String city;
    private String password;




}
