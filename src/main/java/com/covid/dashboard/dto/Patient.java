package com.covid.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Patient {

    private int patientId;

    private User user;
    @JsonProperty
    private boolean isCoronaPositive;

    public boolean getIsCoronaPositive(){
        return isCoronaPositive;
    }

    public void setIsCoronaPositive(boolean isCoronaPositive){
        this.isCoronaPositive = isCoronaPositive;
    }

    private String symptoms;


}
