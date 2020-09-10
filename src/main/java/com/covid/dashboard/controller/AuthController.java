package com.covid.dashboard.controller;

import com.covid.dashboard.dto.LoginRequestDto;
import com.covid.dashboard.dto.LoginResponseDto;
import com.covid.dashboard.security.dto.UserDetailInfo;
import com.covid.dashboard.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/login",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequest) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);
        UserDetailInfo userDetailInfo = (UserDetailInfo)authenticate.getPrincipal();

        String jwtToken = jwtUtil.createToken(authenticate);
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setUserName(userDetailInfo.getUsername());
        loginResponseDto.setFirstName(userDetailInfo.getFirstName());
        loginResponseDto.setLastName(userDetailInfo.getLastName());
        loginResponseDto.setAuthorities(userDetailInfo.getAuthorities());
        loginResponseDto.setJwtToken(jwtToken);
        return ResponseEntity.ok(loginResponseDto);
    }

}
