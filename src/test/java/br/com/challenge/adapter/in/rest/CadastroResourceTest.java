package br.com.challenge.adapter.in.rest;

import br.com.challenge.adapter.dto.CadastroMessageResponse;
import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.dto.CadastroResponse;
import br.com.challenge.adapter.dto.ErrorResponse;
import br.com.challenge.utils.Fixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CadastroResourceTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void shouldCreateCadastro() {
        CadastroRequest request = Fixture.buildCadastroRequest();

        CadastroMessageResponse response = client.post()
                .uri("/cadastro/adicionar")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CadastroMessageResponse.class)
                .returnResult()
                .getResponseBody();

        String expectedMessage = ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale()).getString("received.data.message");

        assertEquals(expectedMessage, response.getMessage());
    }

    @Test
    public void shouldValidateBeanValidations_onCreateCadastro_WhenCadastroRequestIsInvalid() {
        CadastroRequest request = new CadastroRequest("", "", "", "", 10, "");

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
        assertEquals(7, errorResponse.getErrors().size());
    }

    /*@Test
    public void shouldGetCadastroById() {
        String cadastroId = "badaddbd-4e1e-4a20-acbb-823d0ac4fbe6";

        CadastroResponse response = client.get()
                .uri(String.format("/cadastro/%s", cadastroId))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CadastroResponse.class)
                .returnResult()
                .getResponseBody();
    }*/

    @Test
    public void shouldReturnNoContent_onGetCadastroById_whenCadastroNotExist() {
        String cadastroId = "badaddbd-4e1e-4a20-acbb-823d0ac4fbe6";

        WebTestClient.BodyContentSpec response = client.get()
                .uri(String.format("/cadastro/%s", cadastroId))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody();

        assertNull(response.returnResult().getResponseBody());
    }

    @Test
    public void shouldReturnBadRequest_onGetCadastroById_whenCadastroIdIsInvalid() {
        ErrorResponse response = client.get()
                .uri("/cadastro/1234-5689}")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ErrorResponse.class)
                .returnResult()
                .getResponseBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getHttpCode());
        assertEquals("The 'cadastroId' value is invalid", response.getDescription());
        assertTrue(response.getErrors().isEmpty());
    }

}
