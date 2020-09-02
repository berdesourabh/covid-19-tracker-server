package com.covid.dashboard.dto;

import lombok.Data;

@Data
public class CoronaReport {

    private String country;
    private String state;
    private String city;
    private CoronaData coronaData;



}
