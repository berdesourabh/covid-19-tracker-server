package com.covid.dashboard.service;

import com.covid.dashboard.entity.User;
import com.covid.dashboard.repository.UserRegistrationRepository;
import com.covid.dashboard.request.UserRegistrationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    public void registerUser(UserRegistrationRequest userRegistrationRequest){
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.convertValue(userRegistrationRequest, User.class);
        user.setRole("ROLE_USER");
        userRegistrationRepository.save(user);

    }

}
