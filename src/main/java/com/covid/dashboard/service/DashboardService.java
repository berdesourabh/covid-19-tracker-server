package com.covid.dashboard.service;

import com.covid.dashboard.dto.CoronaReport;
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
        List<com.covid.dashboard.dto.Patient> patients = getPatients(patientEntities);
        return patients;

    }


    public List<com.covid.dashboard.dto.Patient> getCoronaPatientsByCountryAndState(String country, String state) {
        List<Patient> patients = patientRepository.findByUser_CountryAndUser_State(country, state);
        List<com.covid.dashboard.dto.Patient> patientsList = getPatients(patients);
        return patientsList;
    }

    public List<com.covid.dashboard.dto.Patient> getCoronaPatientsByCountryAndStateAndCity(String country, String state, String city) {
        List<Patient> patients = patientRepository.findByUser_CountryAndUser_StateAndUser_City(country, state, city);
        List<com.covid.dashboard.dto.Patient> patientsList = getPatients(patients);
        return patientsList;
    }

    private List<com.covid.dashboard.dto.Patient> getPatients(List<Patient> patientEntities) {
        //List<Patient> coronaPatients = patientEntities.stream().filter(patient -> "Y".equals(patient.getCoronaPositive())).collect(Collectors.toList());

        List<Patient> patients = patientEntities.stream().collect(Collectors.toList());

        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).convertValue(patients, new TypeReference<List<com.covid.dashboard.dto.Patient>>() {
        });
    }

    public List<com.covid.dashboard.dto.Patient> getAllPatients() {

        List<Patient> patientEntities = patientRepository.findAll();
        List<com.covid.dashboard.dto.Patient> patients = getPatients(patientEntities);
        return patients;

    }

    public CoronaReport getCoronaPatientsCount(String country, String state, String city) {//queryString = " country=India&state=maha&city=pune "

        CoronaReport coronaReport = new CoronaReport();
        List<com.covid.dashboard.dto.Patient> patients;

        if (StringUtils.hasText(country) && StringUtils.hasText(state) && StringUtils.hasText(city)) {
            //patients = getCoronaPatientsByCountryAndStateAndCity(country, state, city);
            long totalCoronaCases = patientRepository.countByCoronaPositiveAndUser_CountryAndUser_StateAndUser_city("Y",country,
                    state,city);
            long totalRecovered =patientRepository.countByCoronaPositiveAndUser_CountryAndUser_StateAndUser_city("N",country,
                    state,city);
            long totalDeaths = patientRepository.countByDeadAndUser_CountryAndUser_StateAndUser_city(true,country,state,city);
            coronaReport.setCoronaCases(totalCoronaCases);
            coronaReport.setTotalRecovered(totalRecovered);
            coronaReport.setTotalDeaths(totalDeaths);
            return coronaReport;


        } else if (StringUtils.hasText(country) && StringUtils.hasText(state)) {
            //patients = getCoronaPatientsByCountryAndState(country, state);
            long totalCoronaCases = patientRepository.countByCoronaPositiveAndUser_CountryAndUser_State("Y",country,
                    state);
            long totalRecovered =patientRepository.countByCoronaPositiveAndUser_CountryAndUser_State("N",country,
                    state);
            long totalDeaths = patientRepository.countByDeadAndUser_CountryAndUser_State(true,country,state);
            coronaReport.setCoronaCases(totalCoronaCases);
            coronaReport.setTotalRecovered(totalRecovered);
            coronaReport.setTotalDeaths(totalDeaths);
            return coronaReport;


        } else {
            if ("All".equals(country)) {
                //patients = getAllPatients();

                long totalCoronaCases = patientRepository.getAllCoronaPatientCount();
                long totalRecovered = patientRepository.getAllRecoveredPatientsCount();
                long totalDeaths = patientRepository.getAllDeathPatientsCount();
                coronaReport.setCoronaCases(totalCoronaCases);
                coronaReport.setTotalRecovered(totalRecovered);
                coronaReport.setTotalDeaths(totalDeaths);
                return coronaReport;
            } else {
                // = getCoronaPatientsByCountry(country);
                long totalCoronaCases = patientRepository.countByCoronaPositiveAndUser_Country("Y",country);
                long totalRecovered =patientRepository.countByCoronaPositiveAndUser_Country("N",country);
                long totalDeaths = patientRepository.countByDeadAndUser_Country(true,country);
                coronaReport.setCoronaCases(totalCoronaCases);
                coronaReport.setTotalRecovered(totalRecovered);
                coronaReport.setTotalDeaths(totalDeaths);
                return coronaReport;
            }
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
