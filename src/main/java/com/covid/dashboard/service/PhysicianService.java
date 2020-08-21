package com.covid.dashboard.service;

import com.covid.dashboard.dto.Patient;
import com.covid.dashboard.entity.PatientEntity;
import com.covid.dashboard.repository.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhysicianService {

    @Autowired
    private PatientRepository patientRepository;

    public void addCoronaPatient(Patient patient){
        PatientEntity patientEntity = new ObjectMapper().convertValue(patient, PatientEntity.class);
        patientRepository.save(patientEntity);
    }

    public void updatePatientStatus(Patient patient){

        PatientEntity patientEntity = patientRepository.findByUser_uidNumber(patient.getUser().getUidNumber());
        patientEntity.setCoronaPositive(patient.getIsCoronaPositive());
        patientRepository.save(patientEntity);
    }



}
