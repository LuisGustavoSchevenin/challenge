package br.com.challenge.adapter.dto;

public record UserResponse(
        String nome,
        String sobrenome,
        int idade,
        String pais,
        String dataCriacao,
        String dataAtualizacao) {
}
