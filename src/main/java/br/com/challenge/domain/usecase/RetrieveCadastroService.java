package br.com.challenge.domain.usecase;

import br.com.challenge.adapter.dto.CadastroResponse;
import br.com.challenge.adapter.dto.CadastrosResponse;
import br.com.challenge.adapter.out.mapper.CadastroMapper;
import br.com.challenge.adapter.out.persistence.CadastroEntity;
import br.com.challenge.adapter.out.persistence.CadastroRepository;
import br.com.challenge.application.port.in.RetrieveCadastroUseCae;
import br.com.challenge.configuration.annotation.CustomBeanService;
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
