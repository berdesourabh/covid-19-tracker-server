package com.covid.dashboard.service;

import com.covid.dashboard.dto.Patient;
import com.covid.dashboard.entity.PatientEntity;
import com.covid.dashboard.repository.PatientRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getCoronaPatientsByCountry(String country){

        List<PatientEntity> patientEntities = patientRepository.findByUser_Country(country);
        List<PatientEntity> coronaPatients = patientEntities.stream().filter(patientEntity -> patientEntity.isCoronaPositive() == true).collect(Collectors.toList());

        List<Patient> patients = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).convertValue(coronaPatients, new TypeReference<List<Patient>>() {
        });
        return patients;

    }



}
