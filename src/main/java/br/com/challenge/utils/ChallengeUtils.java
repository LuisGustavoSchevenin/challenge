package br.com.challenge.utils;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.ResourceBundle;

public class ChallengeUtils {

    public static String getMessage(String key) {
        return ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale()).getString(key);
    }
}
