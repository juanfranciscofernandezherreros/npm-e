package com.fernandez.fixtures.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultsDTO {
    private String id;
    private Date dateExecuted;
    private String country;
    private String league;
    private String action;
    private List<String> ids;
}

