package com.covid.dashboard.service;

import com.covid.dashboard.dto.UserRegisterResponse;
import com.covid.dashboard.entity.User;
import com.covid.dashboard.repository.UserRepository;
import com.covid.dashboard.request.UserRegistrationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationEmailService verificationEmailService;



    public UserRegisterResponse registerUser(UserRegistrationRequest userRegistrationRequest, HttpServletRequest httpServletRequest){
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.convertValue(userRegistrationRequest, User.class);
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        RandomString randomString = new RandomString();
        String randomStr = randomString.make(60);
        user.setVerificationCode(randomStr);
        user.setEnabled(true);
        userRepository.save(user);

        com.covid.dashboard.dto.User userDto = new com.covid.dashboard.dto.User();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setVerificationCode(randomStr);
  //      String url = "http://"+httpServletRequest.getHeader("host")+"/user/verify";
//        verificationEmailService.sendVerificationEmail(url,userDto);

        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
        userRegisterResponse.setEmail(userRegistrationRequest.getEmail());
        userRegisterResponse.setResponse("User Successfully Registered");
        return userRegisterResponse;
    }


    public String verifyUser(String verificationCode) {
        String message = "";
        User user = userRepository.findUserByVerificationCode(verificationCode);
        if (user != null && user.isEnabled() == false) {
            user.setEnabled(true);
            userRepository.save(user);
            return "Successfully Verified User";
        }
        return "User already verified";
    }


}
