package com.covid.dashboard.service;

import com.covid.dashboard.dto.User;
import com.covid.dashboard.entity.Patient;
import com.covid.dashboard.repository.PatientRepository;
import com.covid.dashboard.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    public void addCoronaPatient(User user){
        User childUser = new User();
        childUser.setEmail(user.getEmail());
        user.getPatient().setUser(childUser);
        Patient byUser_email = patientRepository.findByUser_email(user.getEmail());
        if(null==byUser_email) {
            com.covid.dashboard.entity.User userEntity = new ObjectMapper().convertValue(user, com.covid.dashboard.entity.User.class);
            userEntity.setEnabled(false);
            userEntity = userRepository.save(userEntity);
        }

    }

    public void updatePatientStatus(com.covid.dashboard.dto.Patient patient){

        Patient patientEntity = patientRepository.findByUser_email(patient.getUser().getEmail());
        patientEntity.setCoronaPositive(patient.getCoronaPositive());
        patientRepository.save(patientEntity);
    }



}
