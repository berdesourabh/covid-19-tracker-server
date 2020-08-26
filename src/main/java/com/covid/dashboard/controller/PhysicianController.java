package com.covid.dashboard.controller;

import com.covid.dashboard.dto.Patient;
import com.covid.dashboard.service.PhysicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/physician")
public class PhysicianController {

    @Autowired
    private PhysicianService physicianService;

    @RequestMapping(value = "/addPatient",consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    public void addCoronaPatient(@RequestBody Patient patient){
        physicianService.addCoronaPatient(patient);
    }


    @RequestMapping(value = "/updatePatientStatus",consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.PUT)
    public void updatePatientStatus(@RequestBody Patient patient){
        physicianService.updatePatientStatus(patient);
    }

}