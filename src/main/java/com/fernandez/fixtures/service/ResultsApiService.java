package com.fernandez.fixtures.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernandez.fixtures.dto.BasketballConfigDTO;
import com.fernandez.fixtures.dto.ResultsDTO;
import com.fernandez.fixtures.repository.ResultsIdsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class ResultsApiService {

    private static final String API_URL = "http://localhost:8089/api/npm/start";
    private final ObjectMapper objectMapper;

    @Autowired
    private NpmStartService npmStartService;

    @Autowired
    private ResultsIdsRepository resultsIdsRepository;

    public ResultsApiService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String sendBasketballConfigRequest(ResultsDTO resultsDTO) {
        npmStartService.runNpmStart(resultsDTO.getCountry(),resultsDTO.getLeague(),resultsDTO.getAction(), null,null);
        return "ok";
    }



}

