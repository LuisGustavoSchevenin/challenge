package br.com.challenge.infrastructure.adapter.in.web;

import br.com.challenge.infrastructure.adapter.in.web.dto.CadastroMessageResponse;
import br.com.challenge.infrastructure.adapter.in.web.dto.CadastroRequest;
import br.com.challenge.infrastructure.adapter.in.web.dto.CadastroResponse;
import br.com.challenge.infrastructure.adapter.in.web.dto.CadastrosResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

public interface CadastroResourceI {

    @Operation(summary = "API to submit a Cadastro.")
    ResponseEntity<CadastroMessageResponse> create(final CadastroRequest cadastroRequest);


    @Operation(summary = "API to fetch a Cadastro data.")
    ResponseEntity<CadastroResponse> getByCadastroId(final String cadastroId);

    @Operation(summary = "API to get all Cadastros data.")
    ResponseEntity<CadastrosResponse> getAll();


    @Operation(summary = "API to update an existing Cadastro.")
    ResponseEntity<CadastroResponse> update(final String cadastroId, final CadastroRequest cadastroRequest);

    @Operation(summary = "API to delete an existing Cadastro.")
    ResponseEntity<Void> delete(final String cadastroId);

}
