package com.covid.dashboard.controller;

import com.covid.dashboard.dto.LoginRequestDto;
import com.covid.dashboard.dto.LoginResponseDto;
import com.covid.dashboard.dto.UserLoginResponseDto;
import com.covid.dashboard.security.dto.UserDetailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequest) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);
        UserDetailInfo userDetailInfo = (UserDetailInfo)authenticate.getPrincipal();

        LoginResponseDto response = new LoginResponseDto();
        response.setAuthorities(userDetailInfo.getAuthorities());
        response.setUserName(userDetailInfo.getUsername());

        return response;
    }

}
