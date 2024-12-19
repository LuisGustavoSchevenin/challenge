package br.com.challenge.utils;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class ChallengeUtils {

    public static Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }
}
