package br.com.challenge.infrastructure.adapter.in.web.dto;

public class Error {

    private String field;
    private String message;

    public Error(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
