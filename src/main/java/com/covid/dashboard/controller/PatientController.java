package com.covid.dashboard.controller;

import com.covid.dashboard.dto.PaginationResponse;
import com.covid.dashboard.dto.Patient;
import com.covid.dashboard.dto.PatientRegistrationRequest;
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
    public void addPatient(@RequestBody PatientRegistrationRequest patientRegistrationRequest){
        patientService.addPatient(patientRegistrationRequest);
    }


    @RequestMapping(value = "/patient",consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.PUT)
    public void updatePatientStatus(@RequestBody Patient patient){
        patientService.updatePatientStatus(patient);
    }

    @RequestMapping(value = "/patients/physician/{physicianId}",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    public List<Patient> getAllPatientsByPhysician(@PathVariable("physicianId") String physicianId){
         return patientService.getAllPatientsByPhysician(physicianId);
    }

    @RequestMapping(value = "/patients/{patientId}",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    public Patient getPatient(@PathVariable("patientId") int physicianId){
        return patientService.getPatientById(physicianId);
    }

    @RequestMapping(value = "/patients/pagination",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    public PaginationResponse getAllPatientsByPhysician(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize){
        return patientService.getAllPatients(pageNumber,pageSize);
    }






}
