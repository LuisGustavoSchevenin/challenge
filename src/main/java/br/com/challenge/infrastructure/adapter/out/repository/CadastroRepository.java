package br.com.challenge.infrastructure.adapter.out.repository;

import br.com.challenge.infrastructure.adapter.persistence.entity.CadastroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CadastroRepository extends JpaRepository<CadastroEntity, UUID> {

    CadastroEntity findByCadastroId(String cadastroId);

    List<CadastroEntity> findByNotifiedFalse();
}
