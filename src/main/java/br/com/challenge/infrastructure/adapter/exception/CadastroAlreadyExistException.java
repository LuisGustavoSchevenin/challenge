package br.com.challenge.infrastructure.adapter.exception;

public class CadastroAlreadyExistException extends RuntimeException {

    public CadastroAlreadyExistException(final String message) {
        super(message);
    }
}
