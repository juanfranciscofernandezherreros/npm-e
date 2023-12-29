package com.fernandez.fixtures.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static Instant convertStringToInstant(String dateString) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, FORMATTER);
        return localDateTime.toInstant(ZoneOffset.UTC);
    }

}

