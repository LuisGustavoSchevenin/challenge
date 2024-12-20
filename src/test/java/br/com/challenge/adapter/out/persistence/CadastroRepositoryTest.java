package br.com.challenge.adapter.out.persistence;

import br.com.challenge.utils.Fixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class CadastroRepositoryTest {

    @Autowired
    private CadastroRepository cadastroRepository;

    @Test
    void shouldCreateCadastro() {
        CadastroEntity cadastroEntity = Fixture.buildCadastroEntity("111.222.333.10");

        assertNull(cadastroEntity.getId());
        assertNull(cadastroEntity.getDataCriacao());
        assertNull(cadastroEntity.getDataAtualizacao());

        CadastroEntity entity = cadastroRepository.save(cadastroEntity);

        assertEquals(cadastroEntity.getCadastroId(), entity.getCadastroId());
        assertEquals(cadastroEntity.getCpf(), entity.getCpf());
        assertNotNull(entity.getId());
        assertNotNull(entity.getDataCriacao());
        assertNotNull(entity.getDataAtualizacao());
        assertFalse(entity.isNotified());
    }

    @Test
    void shouldFindByCadastroId() {
        CadastroEntity entity = insertCadastro("111.222.333.11");
        String cadastroId = entity.getCadastroId();

        CadastroEntity cadastroEntity = cadastroRepository.findByCadastroId(cadastroId);

        assertEquals(entity.getId(), cadastroEntity.getId());
        assertEquals(entity.getCadastroId(), cadastroEntity.getCadastroId());
    }

    @Test
    void shouldFindByNotifiedFalse() {
        CadastroEntity entity1 = Fixture.buildCadastroEntity("111.222.333.12");
        entity1.setNotified(true);
        CadastroEntity entity2 = Fixture.buildCadastroEntity("111.222.333.13");

        cadastroRepository.save(entity1);
        cadastroRepository.save(entity2);

        List<CadastroEntity> entities = cadastroRepository.findByNotifiedFalse();

        assertEquals(1, entities.size());
    }

    private CadastroEntity insertCadastro(final String cpf) {
        CadastroEntity entity = Fixture.buildCadastroEntity(cpf);
        return cadastroRepository.save(entity);
    }
}