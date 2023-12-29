package com.fernandez.fixtures.dao.results;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(chain = true)
public class MatchDataDAO {
    public Instant date;
    public TeamDAO home;
    public TeamDAO away;
    public ResultDAO result;
    public String totalLocal;
    public String firstLocal;
    public String secondLocal;
    public String thirstLocal;
    public String fourthLocal;
    public String extraLocal;
    public String totalAway;
    public String firstAway;
    public String secondAway;
    public String thirstAway;
    public String fourthAway;
    public String extraAway;
}

