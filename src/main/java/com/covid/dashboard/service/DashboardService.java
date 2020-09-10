package com.covid.dashboard.service;

import com.covid.dashboard.dto.*;
import com.covid.dashboard.entity.Patient;
import com.covid.dashboard.repository.PatientRepository;
import com.covid.dashboard.repository.RegionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RegionRepository regionRepository;

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
        List<CountryReport> countryReports = new ArrayList<>();

        List<com.covid.dashboard.dto.Patient> patients;

        if (StringUtils.hasText(country) && StringUtils.hasText(state) && StringUtils.hasText(city)) {

            CountryReport countryReport = new CountryReport();
            countryReport.setName(country);
            StateReport stateReport = new StateReport();
            stateReport.setName(state);

            CityReport cityReport = new CityReport();
            cityReport.setCityName(city);

            CoronaData coronaData = new CoronaData();
            //patients = getCoronaPatientsByCountryAndStateAndCity(country, state, city);
            long totalCoronaCases = patientRepository.countByCoronaPositiveAndUser_CountryAndUser_StateAndUser_City("Y",country,
                    state,city);
            long totalRecovered =patientRepository.countByRecoveredAndUser_CountryAndUser_StateAndUser_city("Y",country,
                    state,city);
            long totalDeaths = patientRepository.countByDeadAndUser_CountryAndUser_StateAndUser_city(true,country,state,city);
            coronaData.setCoronaCases(totalCoronaCases);
            coronaData.setTotalRecovered(totalRecovered);
            coronaData.setTotalDeaths(totalDeaths);
            cityReport.setCoronaData(coronaData);
            stateReport.setCityReports(Collections.singletonList(cityReport));
            countryReport.setStateReports(Collections.singletonList(stateReport));
            countryReports.add(countryReport);


        } else if (StringUtils.hasText(country) && StringUtils.hasText(state)) {
            //patients = getCoronaPatientsByCountryAndState(country, state);
            CountryReport countryReport = new CountryReport();
            countryReport.setName(country);
            List<CityReport> cityReports = new ArrayList<>();
            StateReport stateReport = new StateReport();
            stateReport.setName(state);
            List<String> cities = regionRepository.getCitiesByCountryAndState(country, state);
            cities.forEach(c -> {


                CityReport cityReport = new CityReport();
                cityReport.setCityName(c);
                CoronaData coronaData = new CoronaData();

                long totalCoronaCases = patientRepository.countByCoronaPositiveAndUser_CountryAndUser_StateAndUser_City("Y",country,state,c);
                long totalRecovered =patientRepository.countByRecoveredAndUser_CountryAndUser_StateAndUser_city("Y",country,
                        state,c);
                long totalDeaths = patientRepository.countByDeadAndUser_CountryAndUser_StateAndUser_city(true,country,state,c);
                coronaData.setCoronaCases(totalCoronaCases);
                coronaData.setTotalRecovered(totalRecovered);
                coronaData.setTotalDeaths(totalDeaths);
                cityReport.setCoronaData(coronaData);
                cityReports.add(cityReport);

            });
            stateReport.setCityReports(cityReports);
            countryReport.setStateReports(Collections.singletonList(stateReport));
            countryReports.add(countryReport);


        } else {
            if ("All".equals(country)) {
                //patients = getAllPatients();


                List<String> countries = regionRepository.getAllCountries();

                countries.forEach(c -> {

                    CountryReport countryReport = new CountryReport();
                    countryReport.setName(c);
                    CoronaData coronaData = new CoronaData();
                    long totalCoronaCases = patientRepository.countByCoronaPositiveAndUser_Country("Y",c);
                    long totalRecovered =patientRepository.countByRecoveredAndUser_Country("Y",c);
                    long totalDeaths = patientRepository.countByDeadAndUser_Country(true,c);
                    coronaData.setCoronaCases(totalCoronaCases);
                    coronaData.setTotalRecovered(totalRecovered);
                    coronaData.setTotalDeaths(totalDeaths);
                    countryReport.setCoronaData(coronaData);
                    countryReports.add(countryReport);
                });

            } else {
                // = getCoronaPatientsByCountry(country);

                CountryReport countryReport = new CountryReport();
                countryReport.setName(country);
                List<StateReport> stateReports = new ArrayList<>();

                List<String> states = regionRepository.getStatesByCountry(country);

                states.forEach(s -> {
                    StateReport stateReport = new StateReport();
                    stateReport.setName(s);
                    CoronaData coronaData = new CoronaData();

                    long totalCoronaCases = patientRepository.countByCoronaPositiveAndUser_CountryAndUser_State("Y",country,s);
                    long totalRecovered =patientRepository.countByRecoveredAndUser_CountryAndUser_State("Y",country,s);
                    long totalDeaths = patientRepository.countByDeadAndUser_CountryAndUser_State(true,country,s);
                    coronaData.setCoronaCases(totalCoronaCases);
                    coronaData.setTotalRecovered(totalRecovered);
                    coronaData.setTotalDeaths(totalDeaths);
                    stateReport.setCoronaData(coronaData);
                    stateReports.add(stateReport);
                        });

                countryReport.setStateReports(stateReports);
                countryReports.add(countryReport);



            }
        }
        coronaReport.setCountryReports(countryReports);
        return coronaReport;
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
