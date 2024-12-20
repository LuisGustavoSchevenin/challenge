package br.com.challenge.adapter.dto;

public record EmailNotification(String subject, String message, String content) {

    @Override
    public String toString() {
        return """
                %s
                
                Cadastro: %s
                """.formatted(message, content);
    }
}
