package br.com.challenge.adapter.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroRequest(
        @NotBlank(message = "{validation.nome.blank}") String nome,
        @NotBlank(message = "{validation.sobrenome.blank}") String sobrenome,
        @NotBlank(message = "{validation.cpf.blank}")
        @Size(min = 11, max = 14, message = "{validation.cpf.size}") String cpf,
        @NotBlank(message = "{validation.email.blank}")
        @Email(message = "{validation.email.invalid}") String email,
        @Min(value = 18, message = "{validation.idade.min}")
        @Max(value = 120, message = "{validation.idade.max}") int idade,
        @NotBlank(message = "{validation.idade.blank}") String pais) {
}
