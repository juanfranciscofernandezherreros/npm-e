package com.fernandez.fixtures.dao.results;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Document(collection = "RESULTS")
public class ResultsDAO {
    @Id
    @Field("matchId")
    private String matchId;
    private String country;
    private String league;
    public MatchDataDAO matchData;
    public List<StatsPlayerDAO> statsPlayer;
    public List<StatsMatchDAO> statsMatch_all;
    public List<StatsMatchDAO> statsMatch_first;
    public List<StatsMatchDAO> statsMatch_second;
    public List<StatsMatchDAO> statsMtach_thirst;
    public List<StatsMatchDAO> statsMtach_four;
    public List<PointByPointDAO> pointByPoint;

}



