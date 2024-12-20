package br.com.challenge.domain.usecase;

import br.com.challenge.adapter.dto.CadastroMessageResponse;
import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.dto.CadastroResponse;
import br.com.challenge.adapter.dto.CadastrosResponse;
import br.com.challenge.adapter.out.mapper.CadastroMapper;
import br.com.challenge.adapter.out.persistence.CadastroEntity;
import br.com.challenge.adapter.out.persistence.CadastroRepository;
import br.com.challenge.application.port.in.CadastroUseCase;
import br.com.challenge.configuration.annotation.CustomBeanService;
import br.com.challenge.domain.model.Cadastro;
import br.com.challenge.utils.ChallengeUtils;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.ResourceBundle;

@CustomBeanService
public class CadastroService implements CadastroUseCase {

    private final CadastroMapper cadastroMapper;
    private final CadastroRepository cadastroRepository;

    public CadastroService(CadastroMapper cadastroMapper, CadastroRepository cadastroRepository) {
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
