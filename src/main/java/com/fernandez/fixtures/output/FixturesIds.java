package com.fernandez.fixtures.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "FIXTURES_IDS")
public class FixturesIds {
    @Id
    private FixturesIdPKDAO fixturesIdPKDAO;
    private List<String> ids;
}
