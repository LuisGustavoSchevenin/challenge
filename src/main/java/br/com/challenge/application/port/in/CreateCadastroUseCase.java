package br.com.challenge.application.port.in;

import br.com.challenge.infrastructure.adapter.in.web.dto.CadastroMessageResponse;
import br.com.challenge.infrastructure.adapter.in.web.dto.CadastroRequest;

public interface CreateCadastroUseCase {

    CadastroMessageResponse create(CadastroRequest cadastroRequest);

}
