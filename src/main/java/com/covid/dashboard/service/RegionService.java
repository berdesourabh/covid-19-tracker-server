package com.covid.dashboard.service;

import com.covid.dashboard.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;


    public List<String> getAllCountries(){
        return regionRepository.getAllCountries();
    }

    public List<String> getStatesByContry(String country){
        return regionRepository.getStatesByCountry(country);
    }

    public List<String> getCitiesByCountryAndState(String country,String state){
        return regionRepository.getCitiesByCountryAndState(country,state);
    }


}
