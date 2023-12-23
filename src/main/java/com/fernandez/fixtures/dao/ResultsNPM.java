package com.fernandez.fixtures.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "NPM")
public class ResultsNPM {

    private String country;
    private String league;
    private String seasson;
    private String action;
    private String optional;
    private String strContent;
    private List<String> ids;
}
