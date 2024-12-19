package br.com.challenge.application.port.in;

import br.com.challenge.adapter.dto.CadastroMessageResponse;
import br.com.challenge.adapter.dto.CadastroRequest;

public interface CadastroUseCase {

    CadastroMessageResponse create(CadastroRequest cadastroRequest);

    CadastroMessageResponse update(int uuid, CadastroRequest cadastroRequest);
}
