package br.com.challenge.application.port.in;

import br.com.challenge.adapter.dto.CadastroResponse;
import br.com.challenge.adapter.dto.CadastrosResponse;

public interface RetrieveCadastroUseCae {

    CadastroResponse getByCadastroId(String cadastroId);

    CadastrosResponse getAll();
}
