package com.fernandez.fixtures.repository;

import com.fernandez.fixtures.dao.ResultsNPM;
import com.fernandez.fixtures.output.ResultsIds;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultsRepository extends MongoRepository<ResultsIds, Long> {
}
