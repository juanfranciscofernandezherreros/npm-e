package com.fernandez.fixtures.repository;

import com.fernandez.fixtures.output.ResultsIds;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResultsIdsRepository extends MongoRepository<ResultsIds, Long> {
}
