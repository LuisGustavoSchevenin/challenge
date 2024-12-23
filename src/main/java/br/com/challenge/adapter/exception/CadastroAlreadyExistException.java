package br.com.challenge.adapter.exception;

public class CadastroAlreadyExistException extends RuntimeException {

    public CadastroAlreadyExistException(final String message) {
        super(message);
    }
}
