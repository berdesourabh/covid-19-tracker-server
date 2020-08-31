package com.covid.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Patient {

    private int patientId;

    private User user;

    private String coronaPositive;

    private String symptoms;

    private boolean dead;


}
