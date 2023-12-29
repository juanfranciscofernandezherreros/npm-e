package com.fernandez.fixtures.repository;

import com.fernandez.fixtures.dao.results.ResultsDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultsRepository extends MongoRepository<ResultsDAO, Long> {
}