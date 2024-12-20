package br.com.challenge.domain.usecase;

import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.dto.CadastroResponse;
import br.com.challenge.adapter.out.mapper.CadastroMapper;
import br.com.challenge.adapter.out.persistence.CadastroEntity;
import br.com.challenge.adapter.out.persistence.CadastroRepository;
import br.com.challenge.application.port.in.UpdateCadastroUseCase;
import br.com.challenge.configuration.annotation.CustomBeanService;
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
        CadastroResponse response = null;

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
        }

        return response;
    }
}
