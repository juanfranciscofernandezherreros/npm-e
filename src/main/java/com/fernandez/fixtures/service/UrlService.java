package com.fernandez.fixtures.service;

import com.fernandez.fixtures.dao.urls.UrlsDAO;
import com.fernandez.fixtures.repository.UrlsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UrlService {

    private final UrlsRepository urlRepository;

    @Autowired
    public UrlService(UrlsRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public List<UrlsDAO> findByUrlsRegex(String keyword) {
        String regex = ".*" + keyword + ".*";
        return urlRepository.findByUrlsRegex(regex);
    }
}
