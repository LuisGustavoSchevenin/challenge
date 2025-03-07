package br.com.challenge.infrastructure.adapter.exception;

public class InvalidUUIDException extends RuntimeException {

    public InvalidUUIDException(final String message) {
        super(message);
    }
}
