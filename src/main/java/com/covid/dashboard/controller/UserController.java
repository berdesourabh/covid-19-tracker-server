package com.covid.dashboard.controller;

import com.covid.dashboard.request.UserRegistrationRequest;
import com.covid.dashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PreDestroy;

@RestController
@RequestMapping("/register")
public class UserController {

    @Autowired
    private UserService userService;




    @RequestMapping(value = "/user",consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    public void registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest){
        userService.registerUser(userRegistrationRequest);
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("Destroying...");
    }

}
