package com.fernandez.fixtures;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernandez.fixtures.dao.fixtures.FixturesDAO;
import com.fernandez.fixtures.dao.fixtures.FixturesPKDAO;
import com.fernandez.fixtures.dao.results.ResultsDAO;
import com.fernandez.fixtures.dao.urls.UrlsDAO;
import com.fernandez.fixtures.dto.ResultsDTO;
import com.fernandez.fixtures.dto.Root;
import com.fernandez.fixtures.dto.UrlsDTO;
import com.fernandez.fixtures.output.FixturesIdPKDAO;
import com.fernandez.fixtures.output.FixturesIds;
import com.fernandez.fixtures.output.ResultsIdPKDAO;
import com.fernandez.fixtures.output.ResultsIds;
import com.fernandez.fixtures.repository.FixturesIdRepository;
import com.fernandez.fixtures.repository.FixturesRepository;
import com.fernandez.fixtures.repository.ResultsIdsRepository;
import com.fernandez.fixtures.repository.ResultsRepository;
import com.fernandez.fixtures.service.NpmStartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
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

    @Autowired
    private FixturesRepository fixturesRepository;

    @Autowired
    private FixturesIdRepository fixturesIdRepository;

    @PostMapping("/start")
    @Async
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

    @PostMapping("/strings/fixtures")
    @Async
    public void processingFixtures(@RequestBody ResultsDTO resultsDTO) throws IOException {
        log.info("Received strings: {}", resultsDTO);
        String country = resultsDTO.getCountry();
        String league = resultsDTO.getLeague();
        String action = resultsDTO.getAction();
        log.info("Fetching FixturesIds for country: {}, league: {}, action: {}", country, league, action);
        FixturesIdPKDAO fixturesIdPKDAO = new FixturesIdPKDAO();
        fixturesIdPKDAO.setLeague(league);
        fixturesIdPKDAO.setCountry(country);
        fixturesIdPKDAO.setAction(action);
        Optional<FixturesIds> fixturesIds = fixturesIdRepository.findById(fixturesIdPKDAO);
        log.info("Fetching FixturesDAOs for country: {}, league: {}", country, league);
        List<FixturesDAO> ids = fixturesRepository.findByCountryAndLeague(country, league);
        List<String> idsNotInDatabase = new ArrayList<>(fixturesIds.get().getIds());
        idsNotInDatabase.removeAll(ids.stream().map(FixturesDAO::getMatchId).collect(Collectors.toList()));
        log.info("Processing {} IDs not in the database", idsNotInDatabase.size());
        for (String id : idsNotInDatabase) {
            log.info("Processing ID: {}", id);
            npmStartService.runNpmStartWithIdFixtures(country, league, action, id,null);
            log.info("Processing completed for ID: {}", id);
        }
        log.info("Processing finished for all IDs");
    }

    @PostMapping("/strings/results")
    public void processStrings(@RequestBody ResultsDTO resultsDTO) {
        log.info("Received strings: {}", resultsDTO);
        String country = resultsDTO.getCountry();
        String league = resultsDTO.getLeague();
        String action = resultsDTO.getAction();
        log.info("Fetching ResultsIds for country: {}, league: {}, action: {}", country, league, action);
        ResultsIdPKDAO resultsIdPKDAO = new ResultsIdPKDAO();
        resultsIdPKDAO.setLeague(league);
        resultsIdPKDAO.setCountry(country);
        resultsIdPKDAO.setAction(action);
        Optional<ResultsIds> resultsIds = resultsRepository.findById(resultsIdPKDAO);
        log.info("Fetching ResultsDAOs for country: {}, league: {}", country, league);
        List<ResultsDAO> ids = resultRepository.findByCountryAndLeague(country, league);
        List<String> idsNotInDatabase = new ArrayList<>(resultsIds.get().getIds());
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
    @GetMapping("/resultsId/{league}/{country}")
    public ResponseEntity<?> resultsId(@PathVariable("league") String league,@PathVariable("country") String country){
        try {
            ResultsIdPKDAO resultsIdPKDAO = new ResultsIdPKDAO();
            resultsIdPKDAO.setCountry(country);
            resultsIdPKDAO.setLeague(league);
            resultsIdPKDAO.setAction("results");
            Optional<ResultsIds> resultsIds = resultsRepository.findById(resultsIdPKDAO);
            return new ResponseEntity<>(resultsIds.get(), HttpStatus.OK);
        } catch (MyEntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}


