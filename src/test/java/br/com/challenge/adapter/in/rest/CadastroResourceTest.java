package br.com.challenge.adapter.in.rest;

import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.dto.CadastroResponse;
import br.com.challenge.adapter.dto.ErrorResponse;
import br.com.challenge.utils.DateUtilsUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CadastroResourceTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void shouldCreateACadastro() {
        CadastroRequest request = new CadastroRequest("Sarah", "Connor", "123.456.543-22", 20, "Brasil");

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

        assertFalse(result.cadastroId().isBlank());
        assertFalse(result.cpf().contains("."));
        assertEquals(request.nome(), result.nome());
        assertEquals(request.sobrenome(), result.sobrenome());
        assertEquals(request.idade(), result.idade());
        assertEquals(request.pais(), result.pais());
        assertDoesNotThrow(() -> LocalDateTime.parse(result.dataCriacao(), DateUtilsUtils.DATE_TIME_FORMATTER_PATTERN), "");
        assertDoesNotThrow(() -> LocalDateTime.parse(result.dataAtualizacao(), DateUtilsUtils.DATE_TIME_FORMATTER_PATTERN), "");
    }

    @Test
    public void shouldValidateBeanValidations() {
        CadastroRequest request = new CadastroRequest("", "", "", 10, "");

        ErrorResponse errorResponse = client.post()
                .uri("/cadastro/adicionar")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ErrorResponse.class)
                .returnResult()
                .getResponseBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponse.getHttpCode());
        assertEquals("Bad Request", errorResponse.getDescription());
        assertEquals(5, errorResponse.getErrors().size());
    }

}
