package br.com.challenge.application.port.in;

import br.com.challenge.adapter.dto.CadastroMessageResponse;
import br.com.challenge.adapter.dto.CadastroRequest;

public interface CreateCadastroUseCase {

    CadastroMessageResponse create(CadastroRequest cadastroRequest);

}
