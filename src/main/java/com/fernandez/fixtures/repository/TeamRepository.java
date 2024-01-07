package com.fernandez.fixtures.repository;

import com.fernandez.fixtures.dao.results.ResultsDAO;
import com.fernandez.fixtures.dao.teams.TeamsDAO;
import com.fernandez.fixtures.dao.teams.TeamsPKDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends MongoRepository<TeamsDAO, TeamsPKDAO> {
}
