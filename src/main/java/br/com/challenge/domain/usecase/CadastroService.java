package br.com.challenge.domain.usecase;

import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.dto.CadastroResponse;
import br.com.challenge.adapter.out.mapper.CadastroMapper;
import br.com.challenge.adapter.out.persistence.CadastroEntity;
import br.com.challenge.adapter.out.persistence.CadastroRepository;
import br.com.challenge.application.port.in.CadastroUseCase;
import br.com.challenge.configuration.CustomBeanService;
import br.com.challenge.domain.model.Cadastro;
import org.springframework.dao.DataIntegrityViolationException;

import static br.com.challenge.utils.DateUtilsUtils.fromLocalDateTimeToString;

@CustomBeanService
public class CadastroService implements CadastroUseCase {

    private final CadastroMapper cadastroMapper;
    private final CadastroRepository cadastroRepository;

    public CadastroService(CadastroMapper cadastroMapper, CadastroRepository cadastroRepository) {
        this.cadastroMapper = cadastroMapper;
        this.cadastroRepository = cadastroRepository;
    }

    @Override
    public CadastroResponse create(CadastroRequest cadastroRequest) {
        Cadastro cadastro = cadastroMapper.toCadastro(cadastroRequest);
        CadastroEntity cadastroEntity = cadastroMapper.toCadastroEntity(cadastro);

        try {
            CadastroEntity entity = cadastroRepository.save(cadastroEntity);
            cadastro.setDataCriacao(fromLocalDateTimeToString(entity.getDataCriacao()));
            cadastro.setDataAtualizacao(fromLocalDateTimeToString(entity.getDataAtualizacao()));

            return cadastroMapper.toCadastroResponse(cadastro);
        } catch (DataIntegrityViolationException e) {
            throw e;
        } catch (Exception e) {
            System.out.printf("Erro ao realizar o cadastro: %s\n", e); //TODO review and remove sysout
            throw e;
        }
    }

    @Override
    public CadastroResponse update(int cadastroKey, CadastroRequest cadastroRequest) {
        return null;
    }
}
