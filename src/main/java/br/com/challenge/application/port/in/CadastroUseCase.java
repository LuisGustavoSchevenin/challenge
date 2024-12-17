package br.com.challenge.application.port.in;

import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.dto.CadastroResponse;

public interface CadastroUseCase {

    CadastroResponse create(CadastroRequest cadastroRequest);

    CadastroResponse update(int uuid, CadastroRequest cadastroRequest);
}
