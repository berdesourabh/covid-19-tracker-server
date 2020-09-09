package com.covid.dashboard.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginationResponse {

    private long total;
    List<? extends Object> objects;
}
