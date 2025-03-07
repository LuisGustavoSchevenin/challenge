package br.com.challenge.domain.usecase;

import br.com.challenge.infrastructure.adapter.in.web.dto.CadastroResponse;
import br.com.challenge.infrastructure.adapter.in.web.dto.CadastrosResponse;
import br.com.challenge.infrastructure.adapter.persistence.mapper.CadastroMapper;
import br.com.challenge.infrastructure.adapter.persistence.entity.CadastroEntity;
import br.com.challenge.infrastructure.adapter.out.repository.CadastroRepository;
import br.com.challenge.domain.model.Cadastro;
import br.com.challenge.utils.Fixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RetrieveCadastroServiceTest {

    @Mock
    private CadastroRepository cadastroRepository;

    @Mock
    private CadastroMapper cadastroMapper;

    @InjectMocks
    private RetrieveCadastroService retrieveCadastroService;

    @Test
    void shouldGetCadastroByCadastroId() {
        CadastroEntity cadastroEntity = Fixture.buildCadastroEntity();
        String cadastroId = cadastroEntity.getCadastroId();
        Cadastro cadastro = Fixture.buildCadastro();
        CadastroResponse cadastroResponse = Fixture.buildCadastroResponse();

        when(cadastroRepository.findByCadastroId(cadastroId)).thenReturn(cadastroEntity);
        when(cadastroMapper.toCadastro(cadastroEntity)).thenReturn(cadastro);
        when(cadastroMapper.toCadastroResponse(cadastro)).thenReturn(cadastroResponse);

        CadastroResponse response = retrieveCadastroService.getByCadastroId(cadastroId);

        assertNotNull(response);
    }

    @Test
    void shouldGetAllCadastros() {
        List<CadastroEntity> cadastroEntities = List.of(Fixture.buildCadastroEntity(), Fixture.buildCadastroEntity());

        when(cadastroRepository.findAll()).thenReturn(cadastroEntities);
        when(cadastroMapper.toCadastro(any(CadastroEntity.class))).thenReturn(Fixture.buildCadastro());
        when(cadastroMapper.toCadastroResponse(any(Cadastro.class))).thenReturn(Fixture.buildCadastroResponse());

        CadastrosResponse response = retrieveCadastroService.getAll();

        assertEquals(2, response.cadastros().size());
        verify(cadastroMapper, times(2)).toCadastro(any(CadastroEntity.class));
        verify(cadastroMapper, times(2)).toCadastroResponse(any(Cadastro.class));
    }

}