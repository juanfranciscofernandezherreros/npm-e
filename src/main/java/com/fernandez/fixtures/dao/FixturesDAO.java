package com.fernandez.fixtures.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "FIXTURES")
public class FixturesDAO {

    private String country;
    private String league;
    private String action;
    private String matchId;
    private Instant eventTime;
    private String homeTeam;
    private String awayTeam;
    private Boolean hasExecuted;

}
