package com.fernandez.fixtures.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernandez.fixtures.dto.BasketballConfigDTO;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class BasketballApiService {

    private static final String API_URL = "http://localhost:8999/api/basketball/config";
    private final ObjectMapper objectMapper;

    public BasketballApiService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String sendBasketballConfigRequest(List<BasketballConfigDTO> configDTO) {
        try {
            String jsonBody = objectMapper.writeValueAsString(configDTO);

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            return "Status Code: " + statusCode + "\nResponse Body: " + response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during API request: " + e.getMessage();
        }
    }
}

