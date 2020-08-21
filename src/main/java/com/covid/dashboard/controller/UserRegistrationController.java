package com.covid.dashboard.controller;

import com.covid.dashboard.request.UserRegistrationRequest;
import com.covid.dashboard.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class UserRegistrationController {

    @Autowired
    private UserRegistrationService userRegistrationService;


    @RequestMapping(value = "/user",consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    public void registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest){
        userRegistrationService.registerUser(userRegistrationRequest);
    }

}
