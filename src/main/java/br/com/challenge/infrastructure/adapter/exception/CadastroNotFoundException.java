package br.com.challenge.infrastructure.adapter.exception;

public class CadastroNotFoundException extends RuntimeException {

    public CadastroNotFoundException(final String message) {
        super(message);
    }
}
