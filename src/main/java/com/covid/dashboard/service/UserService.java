package com.covid.dashboard.service;

import com.covid.dashboard.dto.Patient;
import com.covid.dashboard.dto.UserRegisterResponse;
import com.covid.dashboard.entity.User;
import com.covid.dashboard.repository.UserRepository;
import com.covid.dashboard.request.UserRegistrationRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
        user.setRole("ROLE_PHYSICIAN");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        RandomString randomString = new RandomString();
        String randomStr = randomString.make(60);
        user.setVerificationCode(randomStr);
        user.setEnabled(false);
        userRepository.save(user);

        com.covid.dashboard.dto.User userDto = new com.covid.dashboard.dto.User();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setVerificationCode(randomStr);
        String redirectUrl = httpServletRequest.getHeader("origin")+"/login";
        String url = "http://"+httpServletRequest.getHeader("host")+"/user/verify";
        verificationEmailService.sendVerificationEmail(url,userDto,redirectUrl);

        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
        userRegisterResponse.setEmail(userRegistrationRequest.getEmail());
        userRegisterResponse.setResponse("User Successfully Registered");
        return userRegisterResponse;
    }


    public String verifyUser(String verificationCode,String redirectUrl) {
        String message = "";
        User user = userRepository.findUserByVerificationCode(verificationCode);
        if (user != null && user.isEnabled() == false) {
            user.setEnabled(true);
            userRepository.save(user);
            return "<h1>Successfully Verified User</h1><br/>Please click on link to <a href='"+redirectUrl+"'>login</a>";
        }
        return "<h1>User already verified</h1> <br/>Please click on link to <a href='"+redirectUrl+"'>login</a>";
    }

    public List<com.covid.dashboard.dto.User> getUsersStartWithFirstName(String name){
        List<User> users = userRepository.findFirst10UserByFirstNameStartsWith(name);
        List<com.covid.dashboard.dto.User> usersDto = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).convertValue(users, new TypeReference<List<com.covid.dashboard.dto.User>>() {
        });
        usersDto.stream().forEach(user -> user.setPassword("ENCRYPTED**************"));
        return usersDto;
    }


}
