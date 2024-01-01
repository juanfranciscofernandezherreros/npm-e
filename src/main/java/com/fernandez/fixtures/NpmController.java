package com.fernandez.fixtures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernandez.fixtures.dao.results.ResultsDAO;
import com.fernandez.fixtures.dao.urls.UrlsDAO;
import com.fernandez.fixtures.dto.ResultsDTO;
import com.fernandez.fixtures.dto.Root;
import com.fernandez.fixtures.dto.UrlsDTO;
import com.fernandez.fixtures.output.ResultsIds;
import com.fernandez.fixtures.repository.ResultsIdsRepository;
import com.fernandez.fixtures.repository.ResultsRepository;
import com.fernandez.fixtures.service.NpmStartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/npm")
@Slf4j
public class NpmController {

    @Autowired
    private NpmStartService npmStartService;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ResultsIdsRepository resultsRepository;

    @Autowired
    private ResultsRepository resultRepository;

    @PostMapping("/start")
    public void startNpm(@RequestBody List<Root> roots) {

        List<CompletableFuture<Boolean>> futures = roots.stream()
                .map(root -> npmStartService.runNpmStartAsync(root.getCountry(), root.getLeague(), root.getAction(), root.getIds(), root.getOptional()))
                .collect(Collectors.toList());

        // CompletableFuture.allOf(...) retorna una nueva CompletableFuture<Void>
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        // Manejar cualquier excepción
        allOf.exceptionally(throwable -> {
            log.error("Error processing CompletableFuture", throwable);
            return null; // Manejar la excepción según sea necesario
        });

        // Esperar a que todas las CompletableFuture se completen sin bloquear el hilo principal
        allOf.join();
    }

    @PostMapping("/strings")
    public void processStrings(@RequestBody ResultsDTO resultsDTO) {
        log.info("Received strings: {}", resultsDTO);

        String country = resultsDTO.getCountry();
        String league = resultsDTO.getLeague();
        String action = resultsDTO.getAction();

        log.info("Fetching ResultsIds for country: {}, league: {}, action: {}", country, league, action);
        ResultsIds resultsIds = resultsRepository.findByCountryAndLeagueAndAction(country, league, action);

        log.info("Fetching ResultsDAOs for country: {}, league: {}", country, league);
        List<ResultsDAO> ids = resultRepository.findByCountryAndLeague(country, league);

        List<String> idsNotInDatabase = new ArrayList<>(resultsIds.getIds());
        idsNotInDatabase.removeAll(ids.stream().map(ResultsDAO::getMatchId).collect(Collectors.toList()));

        log.info("Processing {} IDs not in the database", idsNotInDatabase.size());
        for (String id : idsNotInDatabase) {
            log.info("Processing ID: {}", id);
            npmStartService.runNpmStartWithIdResults(country, league, action, id);
            log.info("Processing completed for ID: {}", id);
        }

        log.info("Processing finished for all IDs");
    }

    @GetMapping("/saveAllUrls")
    public void saveAllUrls(){
        npmStartService.getFirstUrlWithBooleanFalse();
    }


}



