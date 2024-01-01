package com.fernandez.fixtures.utils;

import com.fernandez.fixtures.service.NpmStartService;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageUtils {
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
