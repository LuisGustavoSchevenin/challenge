package br.com.challenge.adapter.in.rest;

import br.com.challenge.adapter.dto.CadastroMessageResponse;
import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.dto.CadastroResponse;
import br.com.challenge.adapter.dto.CadastrosResponse;
import br.com.challenge.adapter.dto.ErrorResponse;
import br.com.challenge.adapter.out.persistence.CadastroEntity;
import br.com.challenge.adapter.out.persistence.CadastroRepository;
import br.com.challenge.utils.Fixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CadastroResourceTest {

    private static final String BASE_PATH = "cadastros";

    @Autowired
    private WebTestClient client;

    @Autowired
    private CadastroRepository cadastroRepository;


    @Test
    public void shouldCreateCadastro() {
        CadastroRequest request = Fixture.buildCadastroRequest();

        CadastroMessageResponse response = client.post()
                .uri(String.format("/%s/%s", BASE_PATH, "adicionar"))
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
                .uri(String.format("/%s/%s", BASE_PATH, "adicionar"))
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

    @Test
    public void shouldGetCadastroById() {
        String cpf = "333.121.443.20";
        CadastroEntity cadastroEntity_1 = insertCadastro(cpf);
        String cadastroId = cadastroEntity_1.getCadastroId();

        CadastroResponse response = client.get()
                .uri(String.format("/%s/%s", BASE_PATH, cadastroId))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CadastroResponse.class)
                .returnResult()
                .getResponseBody();

        assertEquals(cpf, response.cpf());
        assertFalse(response.cadastroId().isBlank());
        assertFalse(response.dataCriacao().isBlank());
        assertFalse(response.dataAtualizacao().isBlank());
    }

    @Test
    public void shouldReturnNoContent_onGetCadastroById_whenCadastroNotExist() {
        String cadastroId = "badaddbd-4e1e-4a20-acbb-823d0ac4fbe6";

        WebTestClient.BodyContentSpec response = client.get()
                .uri(String.format("/%s/%s", BASE_PATH, cadastroId))
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
                .uri(String.format("/%s/%s", BASE_PATH, "1234-5689"))
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

    @Test
    public void shouldGetAllCadastros() {
        insertCadastro("456.121.443.10");
        insertCadastro("156.155.443.12");

        CadastrosResponse response = client.get()
                .uri(String.format("/%s", BASE_PATH))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CadastrosResponse.class)
                .returnResult()
                .getResponseBody();

        assertTrue(response.cadastros().size() >= 2);

        Optional<CadastroResponse> result = response.cadastros().stream().findAny();

        assertNotNull(result.get());
    }

    private CadastroEntity insertCadastro(final String cpf) {
        CadastroEntity entity = Fixture.buildCadastroEntity(cpf);
        return cadastroRepository.save(entity);
    }

}
