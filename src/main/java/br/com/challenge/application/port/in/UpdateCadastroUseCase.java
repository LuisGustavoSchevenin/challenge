package br.com.challenge.application.port.in;

import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.dto.CadastroResponse;

public interface UpdateCadastroUseCase {

    CadastroResponse update(String cadastroId, CadastroRequest cadastroRequest);

}
