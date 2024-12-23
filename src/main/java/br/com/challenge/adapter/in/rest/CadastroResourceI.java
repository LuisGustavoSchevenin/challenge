package br.com.challenge.adapter.in.rest;

import br.com.challenge.adapter.dto.CadastroMessageResponse;
import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.dto.CadastroResponse;
import br.com.challenge.adapter.dto.CadastrosResponse;
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
