package br.com.challenge.domain.usecase;

import br.com.challenge.infrastructure.adapter.in.web.dto.CadastroRequest;
import br.com.challenge.infrastructure.adapter.in.web.dto.CadastroResponse;
import br.com.challenge.infrastructure.adapter.exception.CadastroNotFoundException;
import br.com.challenge.infrastructure.adapter.persistence.mapper.CadastroMapper;
import br.com.challenge.infrastructure.adapter.persistence.entity.CadastroEntity;
import br.com.challenge.infrastructure.adapter.out.repository.CadastroRepository;
import br.com.challenge.application.port.in.UpdateCadastroUseCase;
import br.com.challenge.infrastructure.configuration.annotation.CustomBeanService;
import br.com.challenge.domain.model.Cadastro;

@CustomBeanService
public class UpdateCadastroService implements UpdateCadastroUseCase {

    private final CadastroRepository cadastroRepository;
    private final CadastroMapper cadastroMapper;

    public UpdateCadastroService(CadastroRepository cadastroRepository, CadastroMapper cadastroMapper) {
        this.cadastroRepository = cadastroRepository;
        this.cadastroMapper = cadastroMapper;
    }

    @Override
    public CadastroResponse update(final String cadastroId, final CadastroRequest cadastroUpdateRequest) {
        CadastroResponse response;

        CadastroEntity existingCadastro = cadastroRepository.findByCadastroId(cadastroId);

        if (existingCadastro != null) {
            if (cadastroUpdateRequest.getNome() != null) {
                existingCadastro.setNome(cadastroUpdateRequest.getNome());
            }

            if (cadastroUpdateRequest.getSobrenome() != null) {
                existingCadastro.setSobrenome(cadastroUpdateRequest.getSobrenome());
            }

            if (cadastroUpdateRequest.getEmail() != null) {
                existingCadastro.setEmail(cadastroUpdateRequest.getEmail());
            }

            if (cadastroUpdateRequest.getPais() != null) {
                existingCadastro.setPais(cadastroUpdateRequest.getPais());
            }

            CadastroEntity updatedCadastro = cadastroRepository.save(existingCadastro);
            Cadastro cadastro = cadastroMapper.toCadastro(updatedCadastro);
            response = cadastroMapper.toCadastroResponse(cadastro);
        } else {
            throw new CadastroNotFoundException("The Cadastro for this id not exists");
        }

        return response;
    }
}
