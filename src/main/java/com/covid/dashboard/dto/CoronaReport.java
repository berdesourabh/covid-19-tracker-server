package com.covid.dashboard.dto;

import lombok.Data;

import java.util.List;

@Data
public class CoronaReport {

   private List<CountryReport> countryReports;

}
