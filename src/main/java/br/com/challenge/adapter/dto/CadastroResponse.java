package br.com.challenge.adapter.dto;

public record CadastroResponse(
        String cadastroId,
        String nome,
        String sobrenome,
        String cpf,
        int idade,
        String pais,
        String dataCriacao,
        String dataAtualizacao) {
}