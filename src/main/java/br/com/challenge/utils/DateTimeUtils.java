package br.com.challenge.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static final DateTimeFormatter DATE_TIME_FORMATTER_PATTERN = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static String fromLocalDateTimeToString(final LocalDateTime date) {
        return date.format(DATE_TIME_FORMATTER_PATTERN);
    }

    public static LocalDateTime fromStringToLocalDateTime(final String date) {
        return LocalDateTime.parse(date, DateTimeUtils.DATE_TIME_FORMATTER_PATTERN);
    }
}
