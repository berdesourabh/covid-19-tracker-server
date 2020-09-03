package com.covid.dashboard.dto;

import lombok.Data;

import java.util.List;

@Data
public class CountryReport {

    private String name;
    private List<StateReport> stateReports;
    private CoronaData coronaData;
}
