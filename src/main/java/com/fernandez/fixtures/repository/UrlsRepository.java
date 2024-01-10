package com.fernandez.fixtures.repository;

import com.fernandez.fixtures.dao.urls.UrlsDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlsRepository extends MongoRepository<UrlsDAO, String> {
    List<UrlsDAO> findAllByIsOpenedFalse();

    List<UrlsDAO> findByUrlsRegex(String regex);
}
