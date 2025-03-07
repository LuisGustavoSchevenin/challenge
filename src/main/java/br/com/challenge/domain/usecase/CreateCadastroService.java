package br.com.challenge.domain.usecase;

import br.com.challenge.infrastructure.adapter.in.web.dto.CadastroMessageResponse;
import br.com.challenge.infrastructure.adapter.in.web.dto.CadastroRequest;
import br.com.challenge.infrastructure.adapter.exception.CadastroAlreadyExistException;
import br.com.challenge.infrastructure.adapter.persistence.mapper.CadastroMapper;
import br.com.challenge.infrastructure.adapter.persistence.entity.CadastroEntity;
import br.com.challenge.infrastructure.adapter.out.repository.CadastroRepository;
import br.com.challenge.application.port.in.CreateCadastroUseCase;
import br.com.challenge.infrastructure.configuration.annotation.CustomBeanService;
import br.com.challenge.domain.model.Cadastro;
import br.com.challenge.utils.ChallengeUtils;
import org.springframework.dao.DataIntegrityViolationException;

@CustomBeanService
public class CreateCadastroService implements CreateCadastroUseCase {

    private final CadastroMapper cadastroMapper;
    private final CadastroRepository cadastroRepository;

    public CreateCadastroService(CadastroMapper cadastroMapper, CadastroRepository cadastroRepository) {
        this.cadastroMapper = cadastroMapper;
        this.cadastroRepository = cadastroRepository;
    }

    @Override
    public CadastroMessageResponse create(final CadastroRequest cadastroRequest) {
        Cadastro cadastro = cadastroMapper.toCadastro(cadastroRequest);
        CadastroEntity cadastroEntity = cadastroMapper.toCadastroEntity(cadastro);

        try {
            cadastroRepository.save(cadastroEntity);

            String message = ChallengeUtils.getMessage("received.data.message");

            return cadastroMapper.toCadastroMessageResponse(message);
        } catch (DataIntegrityViolationException e) {
            throw new CadastroAlreadyExistException("A Cadastro for this cpf already exists.");
        }
    }

}
