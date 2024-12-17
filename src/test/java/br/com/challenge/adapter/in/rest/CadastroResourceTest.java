package br.com.challenge.adapter.in.rest;

import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.dto.CadastroResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CadastroResourceTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void shouldCreateACadastro() {
        CadastroRequest request = new CadastroRequest("Sarah", "Connor", "12345654322", 20, "Brasil");

        CadastroResponse result = client.post()
                .uri("/cadastro/adicionar")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(CadastroResponse.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(result);
    }
}
