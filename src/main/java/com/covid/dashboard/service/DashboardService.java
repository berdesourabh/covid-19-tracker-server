package com.covid.dashboard.service;

import com.covid.dashboard.entity.Patient;
import com.covid.dashboard.repository.PatientRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private PatientRepository patientRepository;

    public List<com.covid.dashboard.dto.Patient> getCoronaPatientsByCountry(String country) {

        List<Patient> patientEntities = patientRepository.findByUser_Country(country);
        List<com.covid.dashboard.dto.Patient> patients = getCoronaPatients(patientEntities);
        return patients;

    }


    public List<com.covid.dashboard.dto.Patient> getCoronaPatientsByCountryAndState(String country, String state) {
        List<Patient> patients = patientRepository.findByUser_CountryAndUser_State(country, state);
        List<com.covid.dashboard.dto.Patient> patientsList = getCoronaPatients(patients);
        return patientsList;
    }

    public List<com.covid.dashboard.dto.Patient> getCoronaPatientsByCountryAndStateAndCity(String country, String state, String city) {
        List<Patient> patients = patientRepository.findByUser_CountryAndUser_StateAndUser_City(country, state, city);
        List<com.covid.dashboard.dto.Patient> patientsList = getCoronaPatients(patients);
        return patientsList;
    }

    private List<com.covid.dashboard.dto.Patient> getCoronaPatients(List<Patient> patientEntities) {
        List<Patient> coronaPatients = patientEntities.stream().filter(patient -> "Y".equals(patient.getCoronaPositive())).collect(Collectors.toList());

        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).convertValue(coronaPatients, new TypeReference<List<com.covid.dashboard.dto.Patient>>() {
        });
    }

    public int getCoronaPatientsCount(String country,String state,String city) {//queryString = " country=India&state=maha&city=pune "



        if (StringUtils.hasText(country) && StringUtils.hasText(state) && StringUtils.hasText(city)) {
            List<com.covid.dashboard.dto.Patient> coronaPatientsByCountryAndStateAndCity = getCoronaPatientsByCountryAndStateAndCity(country, state, city);
            return coronaPatientsByCountryAndStateAndCity.size();
        } else if (StringUtils.hasText(country) && StringUtils.hasText(state)) {
            List<com.covid.dashboard.dto.Patient> coronaPatientsByCountryAndState = getCoronaPatientsByCountryAndState(country, state);
            return coronaPatientsByCountryAndState.size();
        } else {
            List<com.covid.dashboard.dto.Patient> coronaPatientsByCountry = getCoronaPatientsByCountry(country);
            return coronaPatientsByCountry.size();
        }


    }


    private void updateParams(String params[], String country, String state, String city) {
        for (int i = 0; i < params.length; i++) {
            String[] keyValues = params[i].split("=");
            String field = keyValues[0];
            String value = keyValues[1];
            if (field.equals("country")) {
                country = value;
            } else if (field.equals("state")) {
                state = value;
            } else {
                city = value;
            }
        }
    }
}
