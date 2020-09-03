package com.covid.dashboard.dto;

import lombok.Data;

import java.util.List;

@Data
public class StateReport {

    private String name;
    private List<CityReport> cityReports;
    private CoronaData coronaData;
}
