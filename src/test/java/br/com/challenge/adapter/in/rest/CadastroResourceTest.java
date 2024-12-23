package br.com.challenge.adapter.in.rest;

import br.com.challenge.adapter.dto.CadastroMessageResponse;
import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.dto.CadastroResponse;
import br.com.challenge.adapter.dto.CadastrosResponse;
import br.com.challenge.adapter.dto.ErrorResponse;
import br.com.challenge.adapter.out.persistence.CadastroEntity;
import br.com.challenge.adapter.out.persistence.CadastroRepository;
import br.com.challenge.utils.Fixture;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CadastroResourceTest {

    private static final String BASE_PATH = "cadastros";

    private WebTestClient client;

    @BeforeEach
    void setUp() throws SSLException {
        SslContext sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        HttpClient httpClient = HttpClient.create().secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));
        client = WebTestClient.bindToServer(new ReactorClientHttpConnector(httpClient)).baseUrl("https://localhost:8080").build();
    }

    @MockitoSpyBean
    private CadastroRepository cadastroRepository;


    @Test
    public void shouldCreate() {
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
    public void shouldReturnBadRequest_onCreateCadastro_whenUseSameCpfTwice() {
        CadastroRequest request_1 = Fixture.buildCadastroRequest("123.123.123.12");
        CadastroRequest request_2 = Fixture.buildCadastroRequest("123.123.123.12");

        CadastroMessageResponse response_1 = client.post()
                .uri(String.format("/%s/%s", BASE_PATH, "adicionar"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request_1)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CadastroMessageResponse.class)
                .returnResult()
                .getResponseBody();

        String expectedMessage = ResourceBundle.getBundle("messages",
                LocaleContextHolder.getLocale()).getString("received.data.message");

        assertEquals(expectedMessage, response_1.getMessage());

        ErrorResponse response_2 = client.post()
                .uri(String.format("/%s/%s", BASE_PATH, "adicionar"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request_2)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ErrorResponse.class)
                .returnResult()
                .getResponseBody();

        assertEquals("A Cadastro for this cpf already exists.", response_2.getDescription());
    }

    @Test
    public void shouldValidateBeanValidations_onCreateCadastro_WhenRequestIsInvalid() {
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

    @Test
    public void shouldUpdateCadastro() {
        CadastroEntity cadastroEntity = insertCadastro("123.321.443.10");
        String cadastroId = cadastroEntity.getCadastroId();
        CadastroRequest request = new CadastroRequest("Nome", "Sobrenome", "new@email.com", "País");

        CadastroResponse response = client.patch()
                .uri(String.format("/%s/%s", BASE_PATH, cadastroId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CadastroResponse.class)
                .returnResult()
                .getResponseBody();

        assertEquals(cadastroEntity.getCadastroId(), response.cadastroId());
        assertEquals(cadastroEntity.getIdade(), response.idade());
        assertNotEquals(cadastroEntity.getNome(), response.nome());
        assertNotEquals(cadastroEntity.getSobrenome(), response.sobrenome());
        assertNotEquals(cadastroEntity.getEmail(), response.email());
        assertNotEquals(cadastroEntity.getPais(), response.pais());
    }

    @Test
    public void shouldReturnNotFound_onUpdateCadastro_whenCadastroNotExist() {
        String cadastroId = "5c092ab7-f1ef-4878-a1ab-ca9fb67f6ddd";
        CadastroRequest request = new CadastroRequest("Nome", "Sobrenome", "new@email.com", "País");

        ErrorResponse response = client.patch()
                .uri(String.format("/%s/%s", BASE_PATH, cadastroId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(ErrorResponse.class)
                .returnResult()
                .getResponseBody();

        assertEquals("The Cadastro for this id not exists", response.getDescription());
    }

    @Test
    public void shouldReturnBadRequest_onUpdateCadastro_whenCadastroRequestHasInvalidData() {
        CadastroEntity cadastroEntity = insertCadastro("123.321.567.10");
        String cadastroId = cadastroEntity.getCadastroId();
        CadastroRequest request = new CadastroRequest("", "", "", "");

        ErrorResponse response = client.patch()
                .uri(String.format("/%s/%s", BASE_PATH, cadastroId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ErrorResponse.class)
                .returnResult()
                .getResponseBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getHttpCode());
        assertEquals("Bad Request", response.getDescription());
        assertEquals(4, response.getErrors().size());
    }

    @Test
    public void shouldDeleteCadastro() {
        CadastroEntity cadastroEntity = insertCadastro("123.321.111.10");
        String cadastroId = cadastroEntity.getCadastroId();

        EntityExchangeResult<byte[]> response = client.delete()
                .uri(String.format("/%s/%s", BASE_PATH, cadastroId))
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody()
                .returnResult();

        assertNull(response.getResponseBody());
    }

    @Test
    public void shouldReturnNotFound_onDeleteCadastro_whenCadastroNotExists() {
        String cadastroId = "5c092ab7-f1ef-4878-a1ab-ca9fb67f6ddd";

        EntityExchangeResult<byte[]> response = client.delete()
                .uri(String.format("/%s/%s", BASE_PATH, cadastroId))
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody()
                .returnResult();

        assertNull(response.getResponseBody());
    }

    private CadastroEntity insertCadastro(final String cpf) {
        CadastroEntity entity = Fixture.buildCadastroEntity(cpf);
        return cadastroRepository.save(entity);
    }

}
