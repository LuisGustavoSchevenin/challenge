package br.com.challenge.utils;

public class StringUtils {

    public static String removeSpecialCharacters(final String content) {
        return content.replaceAll("[^a-zA-Z0-9]", "");
    }

}
