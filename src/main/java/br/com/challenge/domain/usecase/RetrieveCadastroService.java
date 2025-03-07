package br.com.challenge.domain.usecase;

import br.com.challenge.infrastructure.adapter.in.web.dto.CadastroResponse;
import br.com.challenge.infrastructure.adapter.in.web.dto.CadastrosResponse;
import br.com.challenge.infrastructure.adapter.persistence.mapper.CadastroMapper;
import br.com.challenge.infrastructure.adapter.persistence.entity.CadastroEntity;
import br.com.challenge.infrastructure.adapter.out.repository.CadastroRepository;
import br.com.challenge.application.port.in.RetrieveCadastroUseCae;
import br.com.challenge.infrastructure.configuration.annotation.CustomBeanService;
import br.com.challenge.domain.model.Cadastro;

import java.util.List;

@CustomBeanService
public class RetrieveCadastroService implements RetrieveCadastroUseCae {

    private final CadastroRepository cadastroRepository;
    private final CadastroMapper cadastroMapper;

    public RetrieveCadastroService(CadastroRepository cadastroRepository, CadastroMapper cadastroMapper) {
        this.cadastroRepository = cadastroRepository;
        this.cadastroMapper = cadastroMapper;
    }

    @Override
    public CadastroResponse getByCadastroId(final String cadastroId) {
        CadastroResponse response = null;
        CadastroEntity cadastroEntity = cadastroRepository.findByCadastroId(cadastroId);

        if (cadastroEntity != null) {
            Cadastro cadastro = cadastroMapper.toCadastro(cadastroEntity);
            response = cadastroMapper.toCadastroResponse(cadastro);
        }

        return response;
    }

    @Override
    public CadastrosResponse getAll() {
        List<CadastroResponse> cadastroResponses = cadastroRepository.findAll().stream()
                .map(cadastroMapper::toCadastro)
                .map(cadastroMapper::toCadastroResponse)
                .toList();

        return new CadastrosResponse(cadastroResponses);
    }
}
