package com.covid.dashboard.service;

import com.covid.dashboard.entity.User;
import com.covid.dashboard.repository.UserRepository;
import com.covid.dashboard.request.UserRegistrationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(UserRegistrationRequest userRegistrationRequest){
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.convertValue(userRegistrationRequest, User.class);
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }

}
