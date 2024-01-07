package com.fernandez.fixtures.dao.teams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "TEAMS")
public class TeamsDAO {

    @Id
    private TeamsPKDAO teamPKDAO;
    private String country;
    private String name;
}
