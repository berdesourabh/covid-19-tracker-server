package com.covid.dashboard.controller;

import com.covid.dashboard.dto.Patient;
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
    public void addCoronaPatient(@RequestBody Patient patient){
        patientService.addCoronaPatient(patient);
    }


    @RequestMapping(value = "/patient",consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.PUT)
    public void updatePatientStatus(@RequestBody Patient patient){
        patientService.updatePatientStatus(patient);
    }


    @RequestMapping(value = "/patients",consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    public void addCoronaPatients(@RequestBody List<Patient> patients){

        patients.forEach(patient -> patientService.addCoronaPatient(patient));

    }



}
