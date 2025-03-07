package br.com.challenge.domain.usecase;

import br.com.challenge.infrastructure.adapter.in.web.dto.CadastroMessageResponse;
import br.com.challenge.infrastructure.adapter.in.web.dto.CadastroRequest;
import br.com.challenge.infrastructure.adapter.exception.CadastroAlreadyExistException;
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
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InsertCadastroServiceTest {

    @Mock
    private CadastroMapper cadastroMapper;

    @Mock
    private CadastroRepository cadastroRepository;

    @InjectMocks
    private CreateCadastroService cadastroService;

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
    void shouldThrowCadastroAlreadyExistException_whenCpfAlreadyExistsOnDatabase() {
        CadastroRequest cadastroRequest = Fixture.buildCadastroRequest();
        Cadastro cadastro = Fixture.buildCadastro();
        CadastroEntity cadastroEntity = Fixture.buildCadastroEntity();

        when(cadastroMapper.toCadastro(cadastroRequest)).thenReturn(cadastro);
        when(cadastroMapper.toCadastroEntity(cadastro)).thenReturn(cadastroEntity);
        when(cadastroRepository.save(cadastroEntity)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(CadastroAlreadyExistException.class, () -> cadastroService.create(cadastroRequest));
        verify(cadastroMapper, never()).toCadastroResponse(cadastro);
    }

}