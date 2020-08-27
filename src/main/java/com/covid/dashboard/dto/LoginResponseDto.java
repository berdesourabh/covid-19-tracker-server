package com.covid.dashboard.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class LoginResponseDto {

    private String userName;
    private String phoneNumber;

    private Collection<? extends GrantedAuthority> authorities;

}
