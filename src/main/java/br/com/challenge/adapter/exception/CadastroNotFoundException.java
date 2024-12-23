package br.com.challenge.adapter.exception;

public class CadastroNotFoundException extends RuntimeException {

    public CadastroNotFoundException(final String message) {
        super(message);
    }
}
