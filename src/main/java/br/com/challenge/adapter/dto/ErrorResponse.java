package br.com.challenge.adapter.dto;

import java.util.List;

public class ErrorResponse {

    private int httpCode;
    private String description;
    private List<Error> errors;

    public ErrorResponse(int httpCode, String description, List<Error> errors) {
        this.httpCode = httpCode;
        this.description = description;
        this.errors = errors;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public String getDescription() {
        return description;
    }

    public List<Error> getErrors() {
        return errors;
    }
}
