package com.fernandez.fixtures;

import com.fernandez.fixtures.dao.urls.UrlsDAO;
import com.fernandez.fixtures.dto.BasketballConfigDTO;
import com.fernandez.fixtures.dto.ResultsDTO;
import com.fernandez.fixtures.service.BasketballApiService;
import com.fernandez.fixtures.service.NpmStartService;
import com.fernandez.fixtures.service.ResultsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
public class TuControlador {

    @Autowired
    private NpmStartService npmStartService;

    @Autowired
    private BasketballApiService basketballApiService;

    @Autowired
    private ResultsApiService resultsApiService;

    @GetMapping("/findUrlsContainingString/config")
    @Async
    public List<UrlsDAO> findUrlsContainingString() {
        List<UrlsDAO> urlsDAOList = npmStartService.findUrlsContainingString("results");
        List<BasketballConfigDTO> basketballConfigDTOList = new ArrayList<>();
        if (!urlsDAOList.isEmpty()) {
            // Realizar el split de la URL
            for (UrlsDAO urlsDAO : urlsDAOList) {
                String[] urlParts = urlsDAO.getUrls().split("/");
                BasketballConfigDTO basketballConfigDTO = new BasketballConfigDTO();
                basketballConfigDTO.setCountry(urlParts[4]);
                basketballConfigDTO.setCompetition(urlParts[5]);
                basketballConfigDTO.setSeasson("2022-2023");
                basketballConfigDTOList.add(basketballConfigDTO);
            }
            System.out.println(basketballConfigDTOList);
            basketballApiService.sendBasketballConfigRequest(basketballConfigDTOList);
        }

        return urlsDAOList;
    }

    @GetMapping("/findUrlsContainingString/resultsSave")
    @Async
    public List<UrlsDAO> resultsSave() {
        List<UrlsDAO> urlsDAOList = npmStartService.findUrlsContainingString("results");
        List<BasketballConfigDTO> basketballConfigDTOList = new ArrayList<>();
        if (!urlsDAOList.isEmpty()) {
            // Realizar el split de la URL
            for (UrlsDAO urlsDAO : urlsDAOList) {
                String[] urlParts = urlsDAO.getUrls().split("/");
                ResultsDTO resultsDTO = new ResultsDTO();
                resultsDTO.setCountry(urlParts[4]);
                resultsDTO.setLeague(urlParts[5]);
                resultsDTO.setAction("results");
                System.out.println(resultsDTO);
                resultsApiService.sendBasketballConfigRequest(resultsDTO);
            }
        }

        return urlsDAOList;
    }

    @GetMapping("/findUrlsContainingString/fixturesSave")
    @Async
    public List<UrlsDAO> fixturesSave() {
        List<UrlsDAO> urlsDAOList = npmStartService.findUrlsContainingString("fixtures");
        List<BasketballConfigDTO> basketballConfigDTOList = new ArrayList<>();
        if (!urlsDAOList.isEmpty()) {
            // Realizar el split de la URL
            for (UrlsDAO urlsDAO : urlsDAOList) {
                String[] urlParts = urlsDAO.getUrls().split("/");
                ResultsDTO resultsDTO = new ResultsDTO();
                resultsDTO.setCountry(urlParts[4]);
                resultsDTO.setLeague(urlParts[5]);
                resultsDTO.setAction("fixtures");
                System.out.println(resultsDTO);
                resultsApiService.sendBasketballConfigRequest(resultsDTO);
            }
        }

        return urlsDAOList;
    }



}

