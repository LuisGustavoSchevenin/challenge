package br.com.challenge.domain.usecase;

import br.com.challenge.adapter.dto.CadastroMessageResponse;
import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.dto.CadastroResponse;
import br.com.challenge.adapter.dto.CadastrosResponse;
import br.com.challenge.adapter.out.mapper.CadastroMapper;
import br.com.challenge.adapter.out.persistence.CadastroEntity;
import br.com.challenge.adapter.out.persistence.CadastroRepository;
import br.com.challenge.domain.model.Cadastro;
import br.com.challenge.utils.Fixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CadastroServiceTest {

    @Mock
    private CadastroMapper cadastroMapper;

    @Mock
    private CadastroRepository cadastroRepository;

    @InjectMocks
    private CadastroService cadastroService;

    @Test
    void shouldCreateCadastro() {
        CadastroRequest cadastroRequest = Fixture.buildCadastroRequest();
        Cadastro cadastro = Fixture.buildCadastro();
        CadastroEntity cadastroEntity = Fixture.buildCadastroEntity();
        CadastroMessageResponse cadastroMessageResponse = new CadastroMessageResponse("OK");

        when(cadastroMapper.toCadastro(cadastroRequest)).thenReturn(cadastro);
        when(cadastroMapper.toCadastroEntity(cadastro)).thenReturn(cadastroEntity);
        when(cadastroRepository.save(cadastroEntity)).thenReturn(cadastroEntity);
        when(cadastroMapper.toCadastroMessageResponse(anyString())).thenReturn(cadastroMessageResponse);

        CadastroMessageResponse response = cadastroService.create(cadastroRequest);

        assertEquals("OK", response.getMessage());
    }

    @Test
    void shouldThrowDataIntegrityViolationException_whenCreateCadastro() {
        CadastroRequest cadastroRequest = Fixture.buildCadastroRequest();
        Cadastro cadastro = Fixture.buildCadastro();
        CadastroEntity cadastroEntity = Fixture.buildCadastroEntity();

        when(cadastroMapper.toCadastro(cadastroRequest)).thenReturn(cadastro);
        when(cadastroMapper.toCadastroEntity(cadastro)).thenReturn(cadastroEntity);
        when(cadastroRepository.save(cadastroEntity)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> cadastroService.create(cadastroRequest));
        verify(cadastroMapper, never()).toCadastroResponse(cadastro);
    }

    @Test
    void shouldGetCadastroByCadastroId() {
        CadastroEntity cadastroEntity = Fixture.buildCadastroEntity();
        String cadastroId = cadastroEntity.getCadastroId();
        Cadastro cadastro = Fixture.buildCadastro();
        CadastroResponse cadastroResponse = Fixture.buildCadastroResponse();

        when(cadastroRepository.findByCadastroId(cadastroId)).thenReturn(cadastroEntity);
        when(cadastroMapper.toCadastro(cadastroEntity)).thenReturn(cadastro);
        when(cadastroMapper.toCadastroResponse(cadastro)).thenReturn(cadastroResponse);

        CadastroResponse response = cadastroService.getByCadastroId(cadastroId);

        assertNotNull(response);
    }

    @Test
    void shouldGetAllCadastros() {
        List<CadastroEntity> cadastroEntities = List.of(Fixture.buildCadastroEntity(), Fixture.buildCadastroEntity());

        when(cadastroRepository.findAll()).thenReturn(cadastroEntities);
        when(cadastroMapper.toCadastro(any(CadastroEntity.class))).thenReturn(Fixture.buildCadastro());
        when(cadastroMapper.toCadastroResponse(any(Cadastro.class))).thenReturn(Fixture.buildCadastroResponse());

        CadastrosResponse response = cadastroService.getAll();

        assertEquals(2, response.cadastros().size());
        verify(cadastroMapper, times(2)).toCadastro(any(CadastroEntity.class));
        verify(cadastroMapper, times(2)).toCadastroResponse(any(Cadastro.class));
    }
}