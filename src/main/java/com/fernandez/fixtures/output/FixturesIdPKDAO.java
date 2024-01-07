package com.fernandez.fixtures.output;

import lombok.Data;

import java.time.Instant;

@Data
public class FixturesIdPKDAO {
    private String country;
    private String league;
    private String action;
    private Instant date;
}
