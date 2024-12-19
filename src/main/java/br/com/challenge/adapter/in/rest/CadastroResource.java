package br.com.challenge.adapter.in.rest;

import br.com.challenge.adapter.dto.CadastroMessageResponse;
import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.application.port.in.CadastroUseCase;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Resource to manage the Cadastro data on the system.
 */
@RestController
@RequestMapping("/cadastro")
public class CadastroResource {

    private static final Logger LOG = LoggerFactory.getLogger(CadastroResource.class);

    private final CadastroUseCase cadastroUseCase;

    public CadastroResource(CadastroUseCase cadastroUseCase) {
        this.cadastroUseCase = cadastroUseCase;
    }

    /**
     * API to add a new user on the system
     *
     * @param cadastroRequest containing the user data
     * @return {@link CadastroMessageResponse} with a message about the operation status.
     */
    @PostMapping(value = "/adicionar",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CadastroMessageResponse> createCadastro(@RequestBody @Valid final CadastroRequest cadastroRequest) {
        LOG.info("The Cadastro name:{}, cpf:{} will be added on the system.", cadastroRequest.nome(), cadastroRequest.cpf());
        CadastroMessageResponse response = cadastroUseCase.create(cadastroRequest);
        LOG.info("Cadastro submitted successfully for user nome:{}, cpf:{}", cadastroRequest.nome(), cadastroRequest.cpf());

        return ResponseEntity.ok(response);
    }
}
