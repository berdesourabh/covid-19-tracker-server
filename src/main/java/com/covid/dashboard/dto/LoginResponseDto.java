package com.covid.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class LoginResponseDto {

    private String userName;
    private String firstName;
    private String lastName;
    private String jwtToken;
    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;

}
