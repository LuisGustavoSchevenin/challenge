package br.com.challenge.domain.usecase;

import br.com.challenge.adapter.out.persistence.CadastroEntity;
import br.com.challenge.adapter.out.persistence.CadastroRepository;
import br.com.challenge.utils.Fixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteCadastroServiceTest {

    @Mock
    private CadastroRepository cadastroRepository;

    @InjectMocks
    private DeleteCadastroService deleteCadastroService;

    @Test
    void shouldReturnTrue_whenDeleteCadastro() {
        String cadastroId = "5c092ab7-f1ef-4878-a1ab-ca9fb67f6daa";
        CadastroEntity cadastroEntity = Fixture.buildCadastroEntity();

        when(cadastroRepository.findByCadastroId(cadastroId)).thenReturn(cadastroEntity);

        boolean result = deleteCadastroService.delete(cadastroId);

        assertTrue(result);
        verify(cadastroRepository).delete(cadastroEntity);
    }

    @Test
    void shouldReturnFalse_onDeleteCadastro_whenNotFound() {
        String cadastroId = "5c092ab7-f1ef-4878-a1ab-ca9fb67f6daa";
        CadastroEntity cadastroEntity = Fixture.buildCadastroEntity();

        when(cadastroRepository.findByCadastroId(cadastroId)).thenReturn(null);

        boolean result = deleteCadastroService.delete(cadastroId);

        assertFalse(result);
        verify(cadastroRepository, never()).delete(cadastroEntity);
    }

}