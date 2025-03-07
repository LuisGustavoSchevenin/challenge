package br.com.challenge.infrastructure.adapter.exception;

public class JsonParseEmailException extends RuntimeException {

    public JsonParseEmailException(final String message) {
        super(message);
    }
}
