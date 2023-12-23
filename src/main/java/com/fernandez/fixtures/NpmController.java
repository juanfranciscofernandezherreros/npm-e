package com.fernandez.fixtures;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernandez.fixtures.dto.Root;
import com.fernandez.fixtures.service.NpmStartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}



