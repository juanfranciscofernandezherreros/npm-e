package com.fernandez.quart.utils;

import com.fernandez.quart.entity.Urls;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilsClass {

    public UtilsClass() {
    }

    public List<String> listUrls(List<Urls> urls){
        // Utiliza Stream y filter para eliminar las URLs que contienen "#" y tienen más de 5 barras
        List<String> otherParts = urls.stream()
                .map(Urls::getUrl) // Supongamos que hay un método getUrl en la entidad Urls
                .filter(url -> !url.contains("#"))
                .filter(url -> countSlashes(url) > 5)
                .map(this::extractPartAfterFlashscore)
                .collect(Collectors.toList());
        List<String> result = new ArrayList<>();
        for (String cadena : otherParts) {
            String[] partes = cadena.split("/");
            if (partes.length >= 2) {
                String primeraParte = partes[0]; // "europe"
                String segundaParte = partes[1]; // "euroleague" en este caso
                result.add(primeraParte + "," + segundaParte);
            }
        }
        return result;
    }

    // Método para contar las barras ("/") en una URL
    private static int countSlashes(String url) {
        return url.length() - url.replace("/", "").length();
    }

    // Método para extraer la parte después de "https://www.flashscore.com/"
    private String extractPartAfterFlashscore(String url) {
        try {
            URI uri = new URI(url);
            String path = uri.getPath();
            if (path.startsWith("/basketball/")) {
                return path.substring("/basketball/".length());
            }
        } catch (URISyntaxException e) {
            // Manejo de errores si la URL no es válida
        }
        return ""; // Devuelve una cadena vacía si no se puede extraer la parte
    }

}
