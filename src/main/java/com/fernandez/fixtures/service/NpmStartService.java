package com.fernandez.fixtures.service;

import org.bson.types.ObjectId;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import com.fernandez.fixtures.dao.FixturesDAO;
import com.fernandez.fixtures.dao.results.*;
import com.fernandez.fixtures.output.ResultsIds;
import com.fernandez.fixtures.repository.FixturesRepository;
import com.fernandez.fixtures.repository.ResultsIdsRepository;
import com.fernandez.fixtures.repository.ResultsRepository;
import com.fernandez.fixtures.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class NpmStartService {

    @Autowired
    private FixturesRepository fixturesRepository;

    @Autowired
    private ResultsRepository resultRepository;

    @Autowired
    private ResultsIdsRepository resultsRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private final Logger logger = LoggerFactory.getLogger(NpmStartService.class);

    public CompletableFuture<Boolean> runNpmStartAsync(String country, String league, String action, String ids, String optional) {
        return CompletableFuture.supplyAsync(() -> runNpmStart(country, league, action, ids, optional));
    }
    public String runNpmStartWithId(String country, String league, String action, String ids, String optional) {
        String command = new String();
        String output1 = new String();
        try {
            if(action.equals("fixtures")) {
                    command = String.format("npm run start country=%s league=%s action=%s ids=%s headless",
                            country, league, action, ids);
            }

            logger.info("Running npm start with command: {}", command);

            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command)
                    .directory(new File("C:\\Proyectos\\FlashscoreScraping"));

            processBuilder.redirectErrorStream(true); // Redirige la salida de error al flujo de entrada
            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                 output1 = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }

            int exitCode = process.waitFor();
            logger.info("npm start execution completed with exit code: {}", exitCode);
        } catch (IOException | InterruptedException e) {
            logger.error("Error running npm start", e);
        }
        return output1;
    }

    public boolean runNpmStart(String country, String league, String action, String ids, String optional) {
        String command = new String();
        try {
            if(action.equals("results")){
                command = String.format("npm run start country=%s league=%s action=%s headless",
                        country, league, action);
            }
            if(action.equals("fixtures")) {
                if(ids==null) {
                    command = String.format("npm run start country=%s league=%s action=%s headless",
                            country, league, action);
                } else {
                    command = String.format("npm run start country=%s league=%s action=%s ids=%s headless",
                            country, league, action, ids);
                }
            }

            logger.info("Running npm start with command: {}", command);

            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command)
                    .directory(new File("C:\\Proyectos\\FlashscoreScraping"));

            processBuilder.redirectErrorStream(true); // Redirige la salida de error al flujo de entrada

            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String output = reader.lines().collect(Collectors.joining(System.lineSeparator()));
                if (action != null && action.equals("results")) {
                    saveResultsIds(country, league, action, output);
                }
                if (action != null && action.equals("fixtures")) {
                    saveFixtures(country, league, action, output, ids);
                }
            }
            int exitCode = process.waitFor();
            logger.info("npm start execution completed with exit code: {}", exitCode);

            return exitCode == 0;

        } catch (IOException | InterruptedException e) {
            logger.error("Error running npm start", e);
            return false;
        }
    }
    private void saveFixtures(String country, String league, String action, String output, String ids) {
        System.out.println(country);
        System.out.println(league);
        System.out.println(action);
        System.out.println(output);

        String[] lines = output.split("\\r?\\n");

        // Filtrar las líneas que contienen "matchId" y realizar el split
        String[] filteredLines = Arrays.stream(lines)
                .filter(line -> line.contains("matchId"))
                .map(line -> extractContentWithinSingleQuotes(line))
                .toArray(String[]::new);


        // Filtrar las líneas que contienen "eventTime" y realizar el split
        String[] filteredLines1 = Arrays.stream(lines)
                .filter(line -> line.contains("eventTime"))
                .map(line -> extractContentWithinSingleQuotes(line))
                .toArray(String[]::new);

        List<String> partidos = Arrays.stream(filteredLines1).collect(Collectors.toList());
        System.out.println("partidos" + partidos);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM. HH:mm");


        // Filtrar las líneas que contienen "homeTeam" y realizar el split
        String[] filteredLines2 = Arrays.stream(lines)
                .filter(line -> line.contains("homeTeam"))
                .map(line -> extractContentWithinSingleQuotes(line))
                .toArray(String[]::new);

        // Filtrar las líneas que contienen "awayTeam" y realizar el split
        String[] filteredLines3 = Arrays.stream(lines)
                .filter(line -> line.contains("awayTeam"))
                .map(line -> extractContentWithinSingleQuotes(line))
                .toArray(String[]::new);

        // Asegurarse de que todos los arreglos tengan la misma longitud
        int length = Math.min(Math.min(filteredLines.length, filteredLines1.length),
                Math.min(filteredLines2.length, filteredLines3.length));

        List<FixturesDAO> fixturesDAOList = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            FixturesDAO fixturesDAO = new FixturesDAO();
            String matchId = filteredLines[i];
            String match  = runNpmStartWithId(country,league, action, matchId,null);
            String[] event = match.split("\\r?\\n");
            for(String evt : event) {
                if(evt.contains("fechasPartidos")){
                    match = evt.split("-")[1].replace("]","").trim();
                }
            }
            String homeTeam = filteredLines2[i];
            String awayTeam = filteredLines3[i];

            // Supongamos que la cadena de fecha y hora se almacena en una variable llamada 'eventTime'
            String eventTime = match;

            // Define el formato de la cadena de fecha y hora
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

            // Convierte la cadena a un objeto LocalDateTime
            LocalDateTime localDateTime = LocalDateTime.parse(eventTime, formatter);

            // Establece los segundos en "00" y conserva las horas y minutos originales
            LocalDateTime modifiedDateTime = localDateTime.withSecond(0).withNano(0);

            // Convierte el LocalDateTime a un objeto Instant
            Instant instant = modifiedDateTime.atZone(ZoneOffset.UTC).toInstant();

            System.out.println("Instant con segundos en 00: " + instant);

            fixturesDAO.setEventTime(instant);
            fixturesDAO.setCountry(country);
            fixturesDAO.setLeague(league);
            fixturesDAO.setAction(action);
            fixturesDAO.setMatchId(matchId);
            fixturesDAO.setHomeTeam(homeTeam);
            fixturesDAO.setAwayTeam(awayTeam);
            fixturesDAO.setHasExecuted(false);
            fixturesRepository.save(fixturesDAO);
        }

    }

    // Método para extraer contenido dentro de comillas simples
    private String extractContentWithinSingleQuotes(String input) {
        Pattern pattern = Pattern.compile("'(.*?)'");
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? matcher.group(1) : input.trim();
    }

    private ResultsIds saveResultsIds(String country, String league, String action, String output) {
        // Dividir el output en líneas
        String[] lines = output.split("\\r?\\n");
        System.out.println(output);
        // Filtrar y almacenar solo las líneas que comienzan con "g_3_" en un array
        String[] filteredLines = Arrays.stream(lines)
                .filter(line -> line.startsWith("g_3_"))
                .toArray(String[]::new);
        ResultsIds resultsIds = new ResultsIds();
        resultsIds.setCountry(country);
        resultsIds.setLeague(league);
        resultsIds.setAction(action);
        List<String> linesUrls = new ArrayList<>();
        // Ahora `filteredLines` contiene las líneas deseadas
        logger.info("country {}" , country);
        logger.info("league {}" , league);
        logger.info("action {}", action);
        for (String line : filteredLines) {
            linesUrls.add(line);
        }
        resultsIds.setIds(linesUrls);
        // Obtén la fecha actual
        LocalDate fechaActual = LocalDate.now();
        // Formatea la fecha en el formato deseado (YYYY-MM-DD)
        String fechaFormateada = fechaActual.format(DateTimeFormatter.ISO_DATE);
        // Convierte la cadena formateada a Instant
        // Convierte la cadena formateada a LocalDateTime
        LocalDateTime localDateTime = LocalDate.parse(fechaFormateada, DateTimeFormatter.ISO_DATE)
                .atStartOfDay();

        // Convierte LocalDateTime a Instant usando ZoneOffset.UTC
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

        // Imprime el Instant formateado
        logger.info(instant.toString());
        resultsIds.setDateExecuted(instant);
        logger.info("Result {}", resultsIds);
        resultsRepository.save(resultsIds);
        // Emite un evento después de guardar el documento
        return resultsIds;
    }

    public void runNpmStartWithIdResults(String spain, String acb, String results, String s) {
        String command = String.format("npm run start country=%s league=%s action=%s ids=%s includeMatchData=true includeStatsPlayer=false includeStatsMatch=false includePointByPoint=false headless",
                spain, acb, results, s);

        // Define a regular expression to match the content between single quotes
        Pattern pattern = Pattern.compile("'(.*?)'");

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command)
                    .directory(new File("C:\\Proyectos\\FlashscoreScraping"))
                    .redirectErrorStream(true);

            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String output = reader.lines().collect(Collectors.joining(System.lineSeparator()));
                String[] lines = output.split("\\r?\\n");
                List<String> stringList = new ArrayList<>();
                for (int i = 12; i < lines.length; i++) {
                    stringList.add(lines[i]);
                }

                ResultsDAO resultsDAO = getResultsDAO(spain, acb, pattern, stringList);
                System.out.println(resultsDAO);
                    resultRepository.save(resultsDAO);
            }

            int exitCode = process.waitFor();
            logger.info("npm start execution completed with exit code: {}", exitCode);

        } catch (IOException | InterruptedException e) {
            logger.error("Error running npm start", e);
        }
    }

    private static ResultsDAO getResultsDAO(String spain, String acb, Pattern pattern, List<String> stringList) {
        ResultsDAO resultsDAO = new ResultsDAO();
        // Find the index of the first underscore after "g_3"
        String id = stringList.get(0);
        String resultId = id.split(" ")[1];
        resultsDAO.setMatchId(resultId);
        MatchDataDAO matchDataDAO = new MatchDataDAO();
        ResultDAO resultDAO = new ResultDAO();
        resultDAO.setHome(extracted(stringList, pattern,12));
        resultDAO.setAway(extracted(stringList, pattern,18));
        matchDataDAO.setResult(resultDAO);
        TeamDAO teamDAO = new TeamDAO();
        teamDAO.setName(extracted(stringList, pattern,4));
        try {
            byte[] imageId = uploadImageFromUrl(extracted(stringList, pattern,5));
            teamDAO.setImage(imageId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TeamDAO teamAway = new TeamDAO();
        teamAway.setName(extracted(stringList, pattern,8));
        try {
            byte[] imageId = uploadImageFromUrl(extracted(stringList, pattern,9));
            teamAway.setImage(imageId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        matchDataDAO.setHome(teamDAO);
        matchDataDAO.setAway(teamAway);
        matchDataDAO.setDate(DateUtils.convertStringToInstant(extracted(stringList, pattern,2)));
        resultsDAO.setMatchData(matchDataDAO);
        matchDataDAO.setTotalLocal(extracted(stringList, pattern,12));
        matchDataDAO.setFirstLocal(extracted(stringList, pattern,13));
        matchDataDAO.setSecondLocal(extracted(stringList, pattern,14));
        matchDataDAO.setThirstLocal(extracted(stringList, pattern,15));
        matchDataDAO.setFourthLocal(extracted(stringList, pattern,16));
        matchDataDAO.setExtraLocal(extracted(stringList, pattern,17));
        matchDataDAO.setTotalAway(extracted(stringList, pattern,18));
        matchDataDAO.setFirstAway(extracted(stringList, pattern,19));
        matchDataDAO.setSecondAway(extracted(stringList, pattern,20));
        matchDataDAO.setThirstAway(extracted(stringList, pattern,21));
        matchDataDAO.setFourthAway(extracted(stringList, pattern,22));
        matchDataDAO.setExtraAway(extracted(stringList, pattern,23));
        resultsDAO.setCountry(spain);
        resultsDAO.setLeague(acb);
        return resultsDAO;
    }

    private static String extracted(List<String> stringList, Pattern pattern,int id) {
        Matcher matcher = pattern.matcher(stringList.get(id));
        String extractedValue = new String();
        if (matcher.find()) {
            // Extract and print the content between single quotes
            extractedValue = matcher.group(1);
        } else {
            System.out.println("No match found.");
        }
        return extractedValue;
    }

    public static byte[] uploadImageFromUrl(String imageUrl) throws IOException {
        // Descarga la imagen desde la URL
        byte[] imageBytes = downloadImage(imageUrl);

        // Guarda la imagen en MongoDB y devuelve el ObjectId generado
        return imageBytes;
    }

    private static byte[] downloadImage(String imageUrl) throws IOException {
        try (InputStream inputStream = new URL(imageUrl).openStream()) {
            return IOUtils.toByteArray(inputStream);
        }
    }

}
