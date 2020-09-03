package com.covid.dashboard.dto;

import lombok.Data;

@Data
public class CityReport {

    private String cityName;
    private CoronaData coronaData;
}
