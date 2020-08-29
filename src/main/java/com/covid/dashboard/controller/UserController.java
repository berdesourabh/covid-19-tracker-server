package com.covid.dashboard.controller;

import com.covid.dashboard.request.UserRegistrationRequest;
import com.covid.dashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PreDestroy;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;




    @RequestMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    public void registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest){
        userService.registerUser(userRegistrationRequest);
    }


    @RequestMapping(value = "/register/multiple",consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    public void registerUsers(@RequestBody List<UserRegistrationRequest> userRegistrationRequests){
        userRegistrationRequests.forEach(userRegistrationRequest -> userService.registerUser(userRegistrationRequest) );
    }


    @PreDestroy
    public void preDestroy(){
        System.out.println("Destroying...");
    }

}
