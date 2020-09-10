package com.covid.dashboard.security.service;

import com.covid.dashboard.entity.User;
import com.covid.dashboard.repository.UserRepository;
import com.covid.dashboard.security.dto.UserDetailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRegistrationRepository;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userEntityOptional = userRegistrationRepository.findById(email);
        if (userEntityOptional.isPresent()) {
            User user = userEntityOptional.get();
            UserDetailInfo userDetailInfo = new UserDetailInfo();
            userDetailInfo.setUsername(user.getEmail());
            userDetailInfo.setPassword(user.getPassword());
            userDetailInfo.setEnabled(user.isEnabled());
            userDetailInfo.setFirstName(user.getFirstName());
            userDetailInfo.setLastName(user.getLastName());
            userDetailInfo.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
            return userDetailInfo;
        }
        return null;
    }
}
