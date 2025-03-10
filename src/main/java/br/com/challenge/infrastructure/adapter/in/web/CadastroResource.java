package br.com.challenge.infrastructure.adapter.in.web;

import br.com.challenge.infrastructure.adapter.in.web.dto.CadastroMessageResponse;
import br.com.challenge.infrastructure.adapter.in.web.dto.CadastroRequest;
import br.com.challenge.infrastructure.adapter.in.web.dto.CadastroResponse;
import br.com.challenge.infrastructure.adapter.in.web.dto.CadastrosResponse;
import br.com.challenge.infrastructure.adapter.exception.InvalidUUIDException;
import br.com.challenge.infrastructure.adapter.validator.CreateCadastroGroup;
import br.com.challenge.infrastructure.adapter.validator.UpdateCadastroGroup;
import br.com.challenge.application.port.in.CreateCadastroUseCase;
import br.com.challenge.application.port.in.DeleteCadastroUseCase;
import br.com.challenge.application.port.in.RetrieveCadastroUseCae;
import br.com.challenge.application.port.in.UpdateCadastroUseCase;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Resource to manage the Cadastro data on the system.
 */
@RestController
@RequestMapping("/cadastros")
public class CadastroResource implements CadastroResourceI {

    private static final Logger LOG = LoggerFactory.getLogger(CadastroResource.class);

    private final CreateCadastroUseCase createCadastroUseCase;
    private final RetrieveCadastroUseCae retrieveCadastroUseCase;
    private final UpdateCadastroUseCase updateCadastroUseCase;
    private final DeleteCadastroUseCase deleteCadastroUseCase;
    private final MeterRegistry meterRegistry;

    public CadastroResource(CreateCadastroUseCase createCadastroUseCase, RetrieveCadastroUseCae retrieveCadastroUseCae,
                            UpdateCadastroUseCase updateCadastroUseCase, DeleteCadastroUseCase deleteCadastroUseCase,
                            MeterRegistry meterRegistry) {
        this.createCadastroUseCase = createCadastroUseCase;
        this.retrieveCadastroUseCase = retrieveCadastroUseCae;
        this.updateCadastroUseCase = updateCadastroUseCase;
        this.deleteCadastroUseCase = deleteCadastroUseCase;
        this.meterRegistry = meterRegistry;
    }

    /**
     * API to submit a Cadastro.
     *
     * @param cadastroRequest containing the user data.
     * @return {@link CadastroMessageResponse} with a message about the operation status.
     */
    @PostMapping(value = "/adicionar",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CadastroMessageResponse> create(@RequestBody
                                                          @Validated(CreateCadastroGroup.class) final CadastroRequest cadastroRequest) {
        LOG.info("The Cadastro from client name:{}, cpf:{} will be added on the system.",
                cadastroRequest.getNome(), cadastroRequest.getCpf());
        CadastroMessageResponse response = createCadastroUseCase.create(cadastroRequest);
        LOG.info("Cadastro submitted successfully from client nome:{}, cpf:{}",
                cadastroRequest.getNome(), cadastroRequest.getCpf());

        Counter counter = Counter.builder("cadastro_counter")
                .description("Amount of cadastros created")
                .register(meterRegistry);
        counter.increment();

        return ResponseEntity.ok(response);
    }

    /**
     * API to fetch a Cadastro data.
     *
     * @param cadastroId unique id to identify a Cadastro
     * @return {@link CadastroResponse} with the data submitted by the client and the content generated by the system.
     */
    @GetMapping(value = "/{cadastroId}")
    public ResponseEntity<CadastroResponse> getByCadastroId(@PathVariable("cadastroId") final String cadastroId) {
        validateCadastroId(cadastroId);

        int status = HttpStatus.OK.value();
        LOG.info("Fetching Cadastro data cadastroId:{}.", cadastroId);
        CadastroResponse response = retrieveCadastroUseCase.getByCadastroId(cadastroId);

        if (response == null) {
            LOG.info("Cadastro not found for cadastroId:{}", cadastroId);
            status = HttpStatus.NO_CONTENT.value();
        }

        return ResponseEntity.status(status).body(response);
    }

    /**
     * API to get all Cadastros data.
     *
     * @return {@link CadastrosResponse} with Cadastro list.
     */
    @GetMapping
    public ResponseEntity<CadastrosResponse> getAll() {
        LOG.info("Fetching all Cadastros");
        CadastrosResponse response = retrieveCadastroUseCase.getAll();
        LOG.info("{} Cadastros were found", response.cadastros().size());

        Counter counter = Counter.builder("get_all_cadastro_counter")
                .description("Amount of invocations to get all cadastros")
                .register(meterRegistry);
        counter.increment();

        return ResponseEntity.ok(response);
    }

    /**
     * API to update an existing Cadastro.
     *
     * @param cadastroId      unique identifier
     * @param cadastroRequest request with custom data to be updated.
     * @return {@link CadastroResponse} with updated data.
     */
    @PatchMapping(value = "/{cadastroId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CadastroResponse> update(@PathVariable("cadastroId") final String cadastroId,
                                                   @RequestBody
                                                   @Validated(UpdateCadastroGroup.class) final CadastroRequest cadastroRequest) {
        validateCadastroId(cadastroId);

        CadastroResponse response = updateCadastroUseCase.update(cadastroId, cadastroRequest);

        Counter counter = Counter.builder("patch_cadastro_counter")
                .description("Amount of cadastros that was updated")
                .register(meterRegistry);
        counter.increment();

        return ResponseEntity.ok(response);
    }

    /**
     * API to delete an existing Cadastro.
     *
     * @param cadastroId unique identifier
     * @return 204 if the entity was deleted or 404 if the entity not exists.
     */
    @DeleteMapping("/{cadastroId}")
    public ResponseEntity<Void> delete(@PathVariable("cadastroId") final String cadastroId) {
        validateCadastroId(cadastroId);

        int status = deleteCadastroUseCase.delete(cadastroId)
                ? HttpStatus.NO_CONTENT.value()
                : HttpStatus.NOT_FOUND.value();

        return ResponseEntity.status(status).build();
    }


    private void validateCadastroId(final String cadastroId) {
        try {
            UUID.fromString(cadastroId);
        } catch (IllegalArgumentException e) {
            throw new InvalidUUIDException("The 'cadastroId' value is invalid");
        }
    }
}
