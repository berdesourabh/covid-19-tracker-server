package com.covid.dashboard.util;

import com.covid.dashboard.dto.Patient;
import com.covid.dashboard.dto.User;
import com.covid.dashboard.request.UserRegistrationRequest;
import com.covid.dashboard.service.PhysicianService;
import com.covid.dashboard.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Component
public class DummyUserUtil {

    @Autowired
    private UserService userService;

    @Autowired
    private PhysicianService physicianService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostConstruct
    public void addUsersAndPatients(){
        File usersFile = null;
        File patientsFile = null;
        try {
            usersFile = ResourceUtils.getFile("classpath:users.json");
            String userJson = new String(Files.readAllBytes(usersFile.toPath()));
            List<UserRegistrationRequest> userRegistrationRequests = new ObjectMapper().readValue(userJson, new TypeReference<List<UserRegistrationRequest>>() {
            });
            userRegistrationRequests.forEach(user -> userService.registerUser(user,httpServletRequest));

            patientsFile = ResourceUtils.getFile("classpath:patients.json");
            String patientJson = new String(Files.readAllBytes(patientsFile.toPath()));
            List<Patient> patients = new ObjectMapper().readValue(patientJson, new TypeReference<List<Patient>>() {
            });
            patients.forEach(patient -> physicianService.addCoronaPatient(patient));



        }catch (Exception e){
                e.printStackTrace();
        }

    }

}
