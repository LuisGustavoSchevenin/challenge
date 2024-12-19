package br.com.challenge.utils;

public class StringUtils {

    public static String removeSpecialCharacters(final String content) {
        return content.replaceAll("[^a-zA-Z0-9]", "");
    }

    public static String dataMask(final String string) {
        return string; //TODO implement
    }
}
