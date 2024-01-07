package com.fernandez.fixtures.repository;

import com.fernandez.fixtures.dao.fixtures.FixturesDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixturesRepository extends MongoRepository<FixturesDAO, Long> {
}
