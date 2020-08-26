package com.covid.dashboard.service;

import com.covid.dashboard.entity.Patient;
import com.covid.dashboard.repository.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhysicianService {

    @Autowired
    private PatientRepository patientRepository;

    public void addCoronaPatient(com.covid.dashboard.dto.Patient patient){
        Patient patientEntity = new ObjectMapper().convertValue(patient, Patient.class);
        patientRepository.save(patientEntity);
    }

    public void updatePatientStatus(com.covid.dashboard.dto.Patient patient){

        Patient patientEntity = patientRepository.findByUser_email(patient.getUser().getEmail());
        patientEntity.setCoronaPositive(patient.getIsCoronaPositive());
        patientRepository.save(patientEntity);
    }



}
