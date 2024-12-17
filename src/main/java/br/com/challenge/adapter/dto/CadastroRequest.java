package br.com.challenge.adapter.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CadastroRequest(
        @NotBlank(message = "{validation.nome.blank}") String nome,
        @NotBlank(message = "{validation.sobrenome.blank}") String sobrenome,
        @NotBlank(message = "{validation.cpf.blank}") String cpf,
        @Min(value = 18, message = "{validation.idade.min}")
        @Max(value = 120, message = "{validation.idade.max}") int idade,
        @NotBlank(message = "{validation.idade.blank}") String pais) {
}
