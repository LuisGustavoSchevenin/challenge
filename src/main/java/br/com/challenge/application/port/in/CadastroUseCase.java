package br.com.challenge.application.port.in;

import br.com.challenge.adapter.dto.CadastroMessageResponse;
import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.dto.CadastroResponse;
import br.com.challenge.adapter.dto.CadastrosResponse;

public interface CadastroUseCase {

    CadastroMessageResponse create(CadastroRequest cadastroRequest);

    CadastroResponse getByCadastroId(String cadastroId);

    CadastrosResponse getAll();

    CadastroMessageResponse update(int uuid, CadastroRequest cadastroRequest);

}
