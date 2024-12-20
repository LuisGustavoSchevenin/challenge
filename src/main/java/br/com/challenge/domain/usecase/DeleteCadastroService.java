package br.com.challenge.domain.usecase;

import br.com.challenge.adapter.out.persistence.CadastroEntity;
import br.com.challenge.adapter.out.persistence.CadastroRepository;
import br.com.challenge.application.port.in.DeleteCadastroUseCase;
import br.com.challenge.configuration.annotation.CustomBeanService;

@CustomBeanService
public class DeleteCadastroService implements DeleteCadastroUseCase {

    private final CadastroRepository cadastroRepository;

    public DeleteCadastroService(CadastroRepository cadastroRepository) {
        this.cadastroRepository = cadastroRepository;
    }

    @Override
    public boolean delete(final String cadastroId) {
        boolean result = false;
        CadastroEntity cadastroEntity = cadastroRepository.findByCadastroId(cadastroId);

        if (cadastroEntity != null) {
            cadastroRepository.delete(cadastroEntity);
            result = true;
        }

        return result;
    }
}
