package br.com.challenge.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtilsUtils {

    public static String fromLocalDateTimeToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return date.format(formatter);
    }
}
