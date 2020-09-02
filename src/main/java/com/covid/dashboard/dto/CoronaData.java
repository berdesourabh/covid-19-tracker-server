package com.covid.dashboard.dto;

import lombok.Data;

@Data
public class CoronaData {

    private long coronaCases;
    private long totalRecovered;
    private long totalDeaths;
}
