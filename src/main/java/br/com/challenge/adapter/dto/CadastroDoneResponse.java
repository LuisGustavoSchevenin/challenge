package br.com.challenge.adapter.dto;

public record CadastroDoneResponse(
        String cadastroId,
        String nome,
        String sobrenome,
        String cpf,
        String email,
        int idade,
        String pais,
        String dataCriacao,
        String dataAtualizacao) {
}