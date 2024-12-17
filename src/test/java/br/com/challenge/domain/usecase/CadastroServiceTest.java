package br.com.challenge.domain.usecase;

import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.dto.CadastroResponse;
import br.com.challenge.adapter.out.mapper.CadastroMapper;
import br.com.challenge.adapter.out.persistence.CadastroEntity;
import br.com.challenge.adapter.out.persistence.CadastroRepository;
import br.com.challenge.domain.model.Cadastro;
import br.com.challenge.utils.Fixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastroServiceTest {

    @Mock
    private CadastroMapper cadastroMapper;

    @Mock
    private CadastroRepository cadastroRepository;

    @InjectMocks
    private CadastroService cadastroService;

    @Test
    void shouldCreateACadastro() {
        CadastroRequest cadastroRequest = Fixture.buildCadastroRequest();
        Cadastro cadastro = Fixture.buildCadastro();
        CadastroEntity cadastroEntity = Fixture.buildCadastroEntity();
        CadastroResponse cadastroResponse = Fixture.buildCadastroResponse();

        when(cadastroMapper.toCadastro(cadastroRequest)).thenReturn(cadastro);
        when(cadastroMapper.toCadastroEntity(cadastro)).thenReturn(cadastroEntity);
        when(cadastroRepository.save(cadastroEntity)).thenReturn(cadastroEntity);
        when(cadastroMapper.toCadastroResponse(cadastro)).thenReturn(cadastroResponse);

        CadastroResponse response = cadastroService.create(cadastroRequest);

        assertFalse(response.dataCriacao().isBlank());
        assertFalse(response.dataAtualizacao().isBlank());
    }

    @Test
    void shouldThrowDataIntegrityViolationException_whenCreateACadastro() {
        CadastroRequest cadastroRequest = Fixture.buildCadastroRequest();
        Cadastro cadastro = Fixture.buildCadastro();
        CadastroEntity cadastroEntity = Fixture.buildCadastroEntity();

        when(cadastroMapper.toCadastro(cadastroRequest)).thenReturn(cadastro);
        when(cadastroMapper.toCadastroEntity(cadastro)).thenReturn(cadastroEntity);
        when(cadastroRepository.save(cadastroEntity)).thenThrow(DataIntegrityViolationException.class);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> cadastroService.create(cadastroRequest));

        verify(cadastroMapper, never()).toCadastroResponse(cadastro);
    }
}