package com.covid.dashboard.controller;

import com.covid.dashboard.dto.Patient;
import com.covid.dashboard.dto.User;
import com.covid.dashboard.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @RequestMapping(value = "/patient",consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    public void addCoronaPatient(@RequestBody User user){
        patientService.addCoronaPatient(user);
    }


    @RequestMapping(value = "/patient",consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.PUT)
    public void updatePatientStatus(@RequestBody Patient patient){
        patientService.updatePatientStatus(patient);
    }






}
