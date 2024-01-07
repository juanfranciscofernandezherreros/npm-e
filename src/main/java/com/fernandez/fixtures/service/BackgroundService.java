package com.fernandez.fixtures.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class BackgroundService {

    @Async
    public void executeInBackground() {
        // Coloca aquí la lógica de tu tarea en segundo plano
        // Ejemplo: realizar algún proceso que tome un tiempo significativo
    }
}

