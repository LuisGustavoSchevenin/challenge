package br.com.challenge.application.port.in;

import br.com.challenge.infrastructure.adapter.in.web.dto.CadastroResponse;
import br.com.challenge.infrastructure.adapter.in.web.dto.CadastrosResponse;

public interface RetrieveCadastroUseCae {

    CadastroResponse getByCadastroId(String cadastroId);

    CadastrosResponse getAll();
}
