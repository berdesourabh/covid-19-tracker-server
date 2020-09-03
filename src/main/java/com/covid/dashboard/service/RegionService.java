package com.covid.dashboard.service;

import com.covid.dashboard.dto.City;
import com.covid.dashboard.dto.Country;
import com.covid.dashboard.dto.State;
import com.covid.dashboard.repository.RegionRepository;
import com.neovisionaries.i18n.CountryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;


    public List<Country> getAllCountries(){

        List<String> countries = regionRepository.getAllCountries();
        List<Country> ctrys = countries.stream().map(country -> {
            Country countryDto = new Country();
            countryDto.setName(country);
            countryDto.setCode(CountryCode.findByName(country).get(0).toString());
            return countryDto;
        }).collect(Collectors.toList());


        return ctrys;
    }

    public List<State> getStatesByContry(String country){

        List<String> states = regionRepository.getStatesByCountry(country);
        List<State> stateList = states.stream().map(state -> {
            State stateDto = new State();
            stateDto.setName(state);
            return stateDto;
        }).collect(Collectors.toList());

        return stateList;
    }

    public List<City> getCitiesByCountryAndState(String country,String state){

        List<String> cities = regionRepository.getCitiesByCountryAndState(country, state);
        List<City> cityList = cities.stream().map(city -> {
            City cityDto = new City();
            cityDto.setName(city);
            return cityDto;
        }).collect(Collectors.toList());
        return cityList;
    }


}
