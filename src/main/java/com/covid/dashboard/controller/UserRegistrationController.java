package com.covid.dashboard.controller;

import com.covid.dashboard.request.UserRegistrationRequest;
import com.covid.dashboard.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRegistrationController {

    @Autowired
    private UserRegistrationService userRegistrationService;


    @PostMapping(name = "/register",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest){
        userRegistrationService.registerUser(userRegistrationRequest);
    }

}
