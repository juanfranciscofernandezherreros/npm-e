package com.fernandez.fixtures.repository;

import com.fernandez.fixtures.dao.fixtures.FixturesDAO;
import com.fernandez.fixtures.dao.fixtures.FixturesPKDAO;
import com.fernandez.fixtures.output.FixturesIdPKDAO;
import com.fernandez.fixtures.output.FixturesIds;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixturesIdRepository extends MongoRepository<FixturesIds, FixturesIdPKDAO> {
}
