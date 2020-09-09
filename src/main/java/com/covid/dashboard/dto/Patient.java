package com.covid.dashboard.dto;

import lombok.Data;

@Data
public class Patient {

    private int patientId;

    private User user;

    private String coronaPositive;

    private String symptoms;

    private boolean dead;

    private String physicianId;

    private String recovered;

    private String reportStatus;


}
