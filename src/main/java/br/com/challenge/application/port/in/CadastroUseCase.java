package br.com.challenge.application.port.in;

import br.com.challenge.adapter.dto.CadastroMessageResponse;
import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.dto.CadastroResponse;

public interface CadastroUseCase {

    CadastroMessageResponse create(CadastroRequest cadastroRequest);

    CadastroResponse getByCadastroId(String cadastroId);

    CadastroMessageResponse update(int uuid, CadastroRequest cadastroRequest);
}
