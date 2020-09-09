package com.covid.dashboard.controller;


import com.covid.dashboard.dto.City;
import com.covid.dashboard.dto.Country;
import com.covid.dashboard.dto.State;
import com.covid.dashboard.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("/countries")
    public List<Country> getAllCountries(){
        return regionService.getAllCountries();
    }

    @GetMapping("/states/{country}")
    public List<State> getStatesByCountry(@PathVariable("country") String country){
        return regionService.getStatesByContry(country);
    }

    @GetMapping("/cities/{country}/{state}")
    public List<City> getCitiesByCountryAndState(@PathVariable("country") String country, @PathVariable("state") String state){
        return regionService.getCitiesByCountryAndState(country,state);
    }
}
