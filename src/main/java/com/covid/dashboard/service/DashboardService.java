package com.covid.dashboard.service;

import com.covid.dashboard.dto.CoronaData;
import com.covid.dashboard.dto.CoronaReport;
import com.covid.dashboard.entity.Patient;
import com.covid.dashboard.repository.PatientRepository;
import com.covid.dashboard.repository.RegionRepository;
import com.covid.dashboard.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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

    public List<CoronaReport> getCoronaPatientsCount(String country, String state, String city) {//queryString = " country=India&state=maha&city=pune "

        List<CoronaReport> coronaReports = new ArrayList<>();
        List<com.covid.dashboard.dto.Patient> patients;

        if (StringUtils.hasText(country) && StringUtils.hasText(state) && StringUtils.hasText(city)) {
            CoronaReport coronaReport = new CoronaReport();
            coronaReport.setCountry(country);
            coronaReport.setState(state);
            coronaReport.setCity(city);
            CoronaData coronaData = new CoronaData();
            //patients = getCoronaPatientsByCountryAndStateAndCity(country, state, city);
            long totalCoronaCases = patientRepository.countByCoronaPositiveAndUser_CountryAndUser_StateAndUser_city("Y",country,
                    state,city);
            long totalRecovered =patientRepository.countByCoronaPositiveAndUser_CountryAndUser_StateAndUser_city("N",country,
                    state,city);
            long totalDeaths = patientRepository.countByDeadAndUser_CountryAndUser_StateAndUser_city(true,country,state,city);
            coronaData.setCoronaCases(totalCoronaCases);
            coronaData.setTotalRecovered(totalRecovered);
            coronaData.setTotalDeaths(totalDeaths);
            coronaReport.setCoronaData(coronaData);
            coronaReports.add(coronaReport);
            return coronaReports;


        } else if (StringUtils.hasText(country) && StringUtils.hasText(state)) {
            //patients = getCoronaPatientsByCountryAndState(country, state);
            List<String> cities = regionRepository.getCitiesByCountryAndState(country, state);
            cities.forEach(c -> {
                CoronaReport coronaReport = new CoronaReport();
                coronaReport.setCountry(country);
                coronaReport.setState(state);
                coronaReport.setCity(c);
                CoronaData coronaData = new CoronaData();

                long totalCoronaCases = patientRepository.countByCoronaPositiveAndUser_CountryAndUser_StateAndUser_city("Y",country,state,c);
                long totalRecovered =patientRepository.countByCoronaPositiveAndUser_CountryAndUser_StateAndUser_city("N",country,
                        state,c);
                long totalDeaths = patientRepository.countByDeadAndUser_CountryAndUser_StateAndUser_city(true,country,state,c);
                coronaData.setCoronaCases(totalCoronaCases);
                coronaData.setTotalRecovered(totalRecovered);
                coronaData.setTotalDeaths(totalDeaths);
                coronaReport.setCoronaData(coronaData);
                coronaReports.add(coronaReport);

            });

            return coronaReports;


        } else {
            if ("All".equals(country)) {
                //patients = getAllPatients();


                List<String> countries = regionRepository.getAllCountries();

                countries.forEach(c -> {

                    CoronaReport coronaReport = new CoronaReport();
                    coronaReport.setCountry(c);
                    coronaReport.setState(state);
                    coronaReport.setCity(city);
                    CoronaData coronaData = new CoronaData();
                    long totalCoronaCases = patientRepository.countByCoronaPositiveAndUser_Country("Y",c);
                    long totalRecovered =patientRepository.countByCoronaPositiveAndUser_Country("N",c);
                    long totalDeaths = patientRepository.countByDeadAndUser_Country(true,c);
                    coronaData.setCoronaCases(totalCoronaCases);
                    coronaData.setTotalRecovered(totalRecovered);
                    coronaData.setTotalDeaths(totalDeaths);
                    coronaReport.setCoronaData(coronaData);
                    coronaReports.add(coronaReport);
                });

               return coronaReports;
            } else {
                // = getCoronaPatientsByCountry(country);

                List<String> states = regionRepository.getStatesByCountry(country);

                states.forEach(s -> {
                    CoronaReport coronaReport = new CoronaReport();
                    coronaReport.setCountry(country);
                    coronaReport.setState(s);
                    coronaReport.setCity(city);
                    CoronaData coronaData = new CoronaData();

                    long totalCoronaCases = patientRepository.countByCoronaPositiveAndUser_CountryAndUser_State("Y",country,s);
                    long totalRecovered =patientRepository.countByCoronaPositiveAndUser_CountryAndUser_State("N",country,s);
                    long totalDeaths = patientRepository.countByDeadAndUser_CountryAndUser_State(true,country,s);
                    coronaData.setCoronaCases(totalCoronaCases);
                    coronaData.setTotalRecovered(totalRecovered);
                    coronaData.setTotalDeaths(totalDeaths);
                    coronaReport.setCoronaData(coronaData);
                    coronaReports.add(coronaReport);

                        });


                return coronaReports;
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
