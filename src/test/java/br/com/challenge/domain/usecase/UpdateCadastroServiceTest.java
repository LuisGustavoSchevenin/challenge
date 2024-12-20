package br.com.challenge.domain.usecase;

import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.dto.CadastroResponse;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateCadastroServiceTest {

    @Mock
    private CadastroRepository cadastroRepository;

    @Mock
    private CadastroMapper cadastroMapper;

    @InjectMocks
    private UpdateCadastroService updateCadastroService;


    @Test
    void shouldUpdateCadastro() {
        CadastroRequest cadastroRequest = Fixture.buildCadastroRequest();
        String cadastroId = UUID.randomUUID().toString();
        CadastroEntity cadastroEntity = Fixture.buildCadastroEntity();
        Cadastro cadastro = Fixture.buildCadastro();
        CadastroResponse cadastroResponse = Fixture.buildCadastroResponse();

        when(cadastroRepository.findByCadastroId(cadastroId)).thenReturn(cadastroEntity);
        when(cadastroRepository.save(cadastroEntity)).thenReturn(cadastroEntity);
        when(cadastroMapper.toCadastro(cadastroEntity)).thenReturn(cadastro);
        when(cadastroMapper.toCadastroResponse(cadastro)).thenReturn(cadastroResponse);

        CadastroResponse response = updateCadastroService.update(cadastroId, cadastroRequest);

        assertNotNull(response);
        verify(cadastroRepository).findByCadastroId(cadastroId);
        verify(cadastroRepository).save(cadastroEntity);
    }

    @Test
    void shouldNotUpdateCadastro_whenNotFoundCadastro() {
        CadastroRequest cadastroRequest = Fixture.buildCadastroRequest();
        String cadastroId = UUID.randomUUID().toString();

        when(cadastroRepository.findByCadastroId(cadastroId)).thenReturn(null);

        CadastroResponse response = updateCadastroService.update(cadastroId, cadastroRequest);

        assertNull(response);
        verify(cadastroRepository, never()).save(any(CadastroEntity.class));
        verify(cadastroMapper, never()).toCadastro(any(CadastroEntity.class));
        verify(cadastroMapper, never()).toCadastroResponse(any(Cadastro.class));
    }

    @Test
    void shouldNotUpdateCadastro_whenFieldsAreNull() {
        CadastroRequest cadastroRequest = new CadastroRequest(null, null, null, null);
        String cadastroId = UUID.randomUUID().toString();
        CadastroEntity cadastroEntity = Fixture.buildCadastroEntity();
        Cadastro cadastro = Fixture.buildCadastro();
        CadastroResponse cadastroResponse = Fixture.buildCadastroResponse();

        when(cadastroRepository.findByCadastroId(cadastroId)).thenReturn(cadastroEntity);
        when(cadastroRepository.save(cadastroEntity)).thenReturn(cadastroEntity);
        when(cadastroMapper.toCadastro(cadastroEntity)).thenReturn(cadastro);
        when(cadastroMapper.toCadastroResponse(cadastro)).thenReturn(cadastroResponse);

        CadastroResponse response = updateCadastroService.update(cadastroId, cadastroRequest);

        assertNotNull(response);
        verify(cadastroRepository).findByCadastroId(cadastroId);
        verify(cadastroRepository).save(cadastroEntity);
    }
}