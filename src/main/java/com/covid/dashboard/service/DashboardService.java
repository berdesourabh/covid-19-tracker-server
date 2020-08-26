package com.covid.dashboard.service;

import com.covid.dashboard.entity.Patient;
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

    public List<com.covid.dashboard.dto.Patient> getCoronaPatientsByCountry(String country){

        List<Patient> patientEntities = patientRepository.findByUser_Country(country);
        List<Patient> coronaPatients = patientEntities.stream().filter(patient -> patient.isCoronaPositive() == true).collect(Collectors.toList());

        List<com.covid.dashboard.dto.Patient> patients = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).convertValue(coronaPatients, new TypeReference<List<com.covid.dashboard.dto.Patient>>() {
        });
        return patients;

    }



}
