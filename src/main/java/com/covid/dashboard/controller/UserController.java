package com.covid.dashboard.controller;

import com.covid.dashboard.dto.User;
import com.covid.dashboard.dto.UserRegisterResponse;
import com.covid.dashboard.request.UserRegistrationRequest;
import com.covid.dashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;




    @RequestMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    public UserRegisterResponse registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest, HttpServletRequest httpServletRequest){
        return userService.registerUser(userRegistrationRequest,httpServletRequest);
    }


    @RequestMapping(value = "/register/multiple",consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    public void registerUsers(@RequestBody List<UserRegistrationRequest> userRegistrationRequests,HttpServletRequest httpServletRequest){
        userRegistrationRequests.forEach(userRegistrationRequest -> userService.registerUser(userRegistrationRequest,httpServletRequest) );
    }


    @RequestMapping(value = "/verify",method = RequestMethod.GET)
    public String verifyUser(@RequestParam("code")String code,@RequestParam("redirectUrl")String redirectUrl){
        return userService.verifyUser(code,redirectUrl);
    }



    @RequestMapping(value = "/{firstName}",method = RequestMethod.GET)
    public List<User> getAllUsersStartsWithFirstName(@PathVariable("firstName")String firstName){
        return userService.getUsersStartWithFirstName(firstName);
    }
    @PreDestroy
    public void preDestroy(){
        System.out.println("Destroying...");
    }

}
