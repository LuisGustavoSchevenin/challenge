package br.com.challenge.adapter.dto;

public record UserRequest(
        String nome,
        String sobrenome,
        int idade,
        String pais) {
}
