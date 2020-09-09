package com.covid.dashboard.service;

import com.covid.dashboard.dto.PaginationResponse;
import com.covid.dashboard.dto.User;
import com.covid.dashboard.entity.Patient;
import com.covid.dashboard.repository.PatientRepository;
import com.covid.dashboard.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    public void addPatient(User user){
        User childUser = new User();
        childUser.setEmail(user.getEmail());
        user.getPatient().setUser(childUser);

        Patient byUser_email = patientRepository.findByUser_email(user.getEmail());
        if(null==byUser_email) {
            com.covid.dashboard.entity.User userEntity = new ObjectMapper().convertValue(user, com.covid.dashboard.entity.User.class);
            userEntity.getPatient().setReportStatus("WAITING FOR RESULTS");
            userEntity.setEnabled(false);
            userEntity.setRole("ROLE_PATIENT");
            userEntity = userRepository.save(userEntity);
        }

    }

    public void updatePatientStatus(com.covid.dashboard.dto.Patient patient){

        Patient patientEntity = patientRepository.findByUser_email(patient.getUser().getEmail());
        patientEntity = new ObjectMapper().convertValue(patient, Patient.class);
        patientRepository.save(patientEntity);
    }


    public List<com.covid.dashboard.dto.Patient> getAllPatientsByPhysician(String physicianId){

        List<Patient> patientsEnity = patientRepository.findByPhysicianId(physicianId);

        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).convertValue(patientsEnity, new TypeReference<List<com.covid.dashboard.dto.Patient>>() {
        });
    }

    public PaginationResponse getAllPatients(int pageNumber,int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Patient> all = patientRepository.findAll(pageRequest);


        List<com.covid.dashboard.dto.Patient> patients = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).convertValue(all.getContent(), new TypeReference<List<com.covid.dashboard.dto.Patient>>() {
        });
        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setTotal(all.getTotalElements());
        paginationResponse.setObjects(patients);
        return paginationResponse;
    }


}
