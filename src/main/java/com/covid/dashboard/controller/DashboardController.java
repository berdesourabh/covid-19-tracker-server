package com.covid.dashboard.controller;

import com.covid.dashboard.dto.Patient;
import com.covid.dashboard.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;


    @RequestMapping(value = "/patients/country/{country}")
    public List<Patient> getCoronaPatientByCountry(@PathVariable("country") String country) {

        return dashboardService.getCoronaPatientsByCountry(country);
    }

    @RequestMapping(value = "/patients/country/{country}/state/{state}")
    public List<Patient> getCoronaPatientByCountryAndState(@PathVariable("country") String country, @PathVariable("state") String state) {

        return dashboardService.getCoronaPatientsByCountryAndState(country, state);
    }

    @RequestMapping(value = "/patients/country/{country}/state/{state}/city/{city}")
    public List<Patient> getCoronaPatientByCountryAndStateAndCity(@PathVariable("country") String country, @PathVariable("state") String state, @PathVariable("city") String city) {

        return dashboardService.getCoronaPatientsByCountryAndStateAndCity(country, state, city);
    }

    @RequestMapping(value = "/patients")
    public int getCoronaPatientsCounty(@RequestParam(value = "country",required = false)String country, @RequestParam(value = "state",required = false)String state, @RequestParam(value = "city",required = false)String city) {

        return dashboardService.getCoronaPatientsCount(country,state,city);

    }

}
