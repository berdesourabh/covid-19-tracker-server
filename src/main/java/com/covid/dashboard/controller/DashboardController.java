package com.covid.dashboard.controller;

import com.covid.dashboard.dto.Patient;
import com.covid.dashboard.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @PostConstruct
    public void postConstruct(){
        System.exit(1);
    }

    @RequestMapping(value = "/patients/country/{country}")
    public List<Patient> getCoronaPatientByCountry(@PathVariable("country") String country) {

        return dashboardService.getCoronaPatientsByCountry(country);
    }


}
