package com.fernandez.fixtures.dao.fixtures;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "FIXTURES")
public class FixturesDAO {
    @Id
    private FixturesPKDAO fixturesPKDAO;
    private String country;
    private String league;
    private String action;
    private Instant eventTime;
    private String homeTeam;
    private String awayTeam;
    private Boolean hasExecuted;

}
