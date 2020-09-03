package com.covid.dashboard.controller;

import com.covid.dashboard.dto.CoronaReport;
import com.covid.dashboard.dto.Patient;
import com.covid.dashboard.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
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
    public CoronaReport getCoronaPatientsData(@RequestParam(value = "country",required = false)String country, @RequestParam(value = "state",required = false)String state, @RequestParam(value = "city",required = false)String city) {

        return dashboardService.getCoronaPatientsCount(country,state,city);

    }

}
