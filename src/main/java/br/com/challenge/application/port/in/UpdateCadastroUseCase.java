package br.com.challenge.application.port.in;

import br.com.challenge.infrastructure.adapter.in.web.dto.CadastroRequest;
import br.com.challenge.infrastructure.adapter.in.web.dto.CadastroResponse;

public interface UpdateCadastroUseCase {

    CadastroResponse update(String cadastroId, CadastroRequest cadastroRequest);

}
