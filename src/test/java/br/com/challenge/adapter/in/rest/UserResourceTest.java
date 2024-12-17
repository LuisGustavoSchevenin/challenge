package br.com.challenge.adapter.in.rest;

import br.com.challenge.adapter.dto.UserRequest;
import br.com.challenge.adapter.dto.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserResourceTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void shouldCreateAnUSer() {
        UserRequest request = new UserRequest("Sarah", "Connor", 20, "Brasil");

        UserResponse result = client.post()
                .uri("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(UserResponse.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(result);
    }
}
