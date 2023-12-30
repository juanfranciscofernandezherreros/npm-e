package com.fernandez.fixtures.repository;

import com.fernandez.fixtures.output.ResultsIds;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultsIdsRepository extends MongoRepository<ResultsIds, Long> {
    ResultsIds findByCountryAndLeagueAndAction(String country, String league, String action);

}
