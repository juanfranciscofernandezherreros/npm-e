package com.fernandez.fixtures.utils;

public class Utils {

    public static String extractUrl(String input) {
        // Definir el patrón para buscar la URL
        String pattern = "Enlace extraído que cumple con los criterios: ";
        // Dividir la cadena basándose en el patrón
        String[] parts = input.split(pattern);

        // Verificar si se encontró la parte adecuada y extraer la URL
        if (parts.length > 1) {
            String extractedUrl = parts[1].trim();
            return extractedUrl;
        } else {
            // Manejar el caso en el que el patrón no se encuentra en la cadena
            return null;
        }
    }
}

