package com.fernandez.fixtures;

import com.fernandez.fixtures.dao.urls.UrlsDAO;
import com.fernandez.fixtures.dto.BasketballConfigDTO;
import com.fernandez.fixtures.dto.ResultsDTO;
import com.fernandez.fixtures.service.BasketballApiService;
import com.fernandez.fixtures.service.NpmStartService;
import com.fernandez.fixtures.service.ResultsApiService;
import com.fernandez.fixtures.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UrlController {

    @Autowired
    private UrlService urlService;

    @GetMapping("/search")
    public List<UrlsDAO> searchByUrl(@RequestParam String regex) {
        return urlService.findByUrlsRegex("/spain\\/acb-.*\\/results/");
    }

}

