package com.fernandez.quart.repository;

import com.fernandez.quart.entity.Urls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlsRepository extends JpaRepository<Urls, Long> {

}
