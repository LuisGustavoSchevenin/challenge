package br.com.challenge.domain.usecase;

import br.com.challenge.adapter.dto.CadastroMessageResponse;
import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.out.mapper.CadastroMapper;
import br.com.challenge.adapter.out.persistence.CadastroEntity;
import br.com.challenge.adapter.out.persistence.CadastroRepository;
import br.com.challenge.application.port.in.CreateCadastroUseCase;
import br.com.challenge.configuration.annotation.CustomBeanService;
import br.com.challenge.domain.model.Cadastro;
import br.com.challenge.utils.ChallengeUtils;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ResourceBundle;

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

            String message = ResourceBundle.getBundle("messages", ChallengeUtils.getCurrentLocale())
                    .getString("received.data.message");

            return cadastroMapper.toCadastroMessageResponse(message);
        } catch (DataIntegrityViolationException e) { //TODO review ex
            throw e;
        } catch (Exception e) {
            System.out.printf("Erro ao realizar o cadastro: %s\n", e); //TODO review and remove sysout
            throw e;
        }
    }

}
