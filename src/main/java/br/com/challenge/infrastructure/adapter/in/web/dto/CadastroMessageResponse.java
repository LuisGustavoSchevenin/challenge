package br.com.challenge.infrastructure.adapter.in.web.dto;

public class CadastroMessageResponse {

    private String message;

    public CadastroMessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
