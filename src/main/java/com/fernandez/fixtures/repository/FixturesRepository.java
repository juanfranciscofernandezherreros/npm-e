package com.fernandez.fixtures.repository;

import com.fernandez.fixtures.dao.fixtures.FixturesDAO;
import com.fernandez.fixtures.dao.fixtures.FixturesPKDAO;
import com.fernandez.fixtures.dao.results.ResultsDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FixturesRepository extends MongoRepository<FixturesDAO, FixturesPKDAO> {
    List<FixturesDAO> findByCountryAndLeague(String country, String league);

}
