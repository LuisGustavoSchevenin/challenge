package br.com.challenge.utils;

import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.dto.CadastroDoneResponse;
import br.com.challenge.adapter.out.persistence.CadastroEntity;
import br.com.challenge.domain.model.Cadastro;

import java.time.LocalDateTime;
import java.util.UUID;

public class Fixture {

    public static CadastroRequest buildCadastroRequest() {
        return new CadastroRequest("Sarah", "Connor", "123.456.789-10", "test@test.com",20, "Brasil");
    }

    public static Cadastro buildCadastro() {
        return new Cadastro.Builder()
                .nome("Sarah")
                .sobrenome("Connor")
                .cpf("123.456.789.10")
                .email("test@test.com")
                .idade(20)
                .pais("Brasil")
                .build();
    }

    public static CadastroEntity buildCadastroEntity() {
        CadastroEntity cadastroEntity = new CadastroEntity();
        cadastroEntity.setId(UUID.randomUUID());
        cadastroEntity.setCadastroId(UUID.randomUUID().toString());
        cadastroEntity.setNome("Sarah");
        cadastroEntity.setSobrenome("Connor");
        cadastroEntity.setCpf("123.456.789.10");
        cadastroEntity.setEmail("test@test.com");
        cadastroEntity.setIdade(20);
        cadastroEntity.setPais("Brasil");
        cadastroEntity.setDataCriacao(LocalDateTime.now());
        cadastroEntity.setDataAtualizacao(LocalDateTime.now());

        return cadastroEntity;
    }

    public static CadastroDoneResponse buildCadastroDoneResponse() {
        return new CadastroDoneResponse("12345621",
                "Sarah",
                "Connor",
                "12345678910",
                "test@test.com",
                20,
                "Brasil",
                "17/12/2024 00:01:00",
                "17/12/2024 00:01:01");
    }
}
