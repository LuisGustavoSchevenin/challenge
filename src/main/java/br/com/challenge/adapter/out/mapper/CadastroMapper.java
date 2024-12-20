package br.com.challenge.adapter.out.mapper;

import br.com.challenge.adapter.dto.CadastroMessageResponse;
import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.dto.CadastroResponse;
import br.com.challenge.adapter.out.persistence.CadastroEntity;
import br.com.challenge.domain.model.Cadastro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CadastroMapper {

    @Mapping(target = "cadastroId", ignore = true)
    @Mapping(target = "notified", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "cpf", expression = "java( br.com.challenge.utils.StringUtils.removeSpecialCharacters(cadastroRequest.getCpf()))")
    Cadastro toCadastro(CadastroRequest cadastroRequest);

    Cadastro toCadastro(CadastroEntity cadastroEntity);

    @Mapping(target = "id", ignore = true)
    CadastroEntity toCadastroEntity(Cadastro cadastro);

    @Mapping(target = "dataCriacao", source = "dataCriacao", dateFormat = "dd/MM/yyyy HH:mm:ss")
    @Mapping(target = "dataAtualizacao", source = "dataAtualizacao", dateFormat = "dd/MM/yyyy HH:mm:ss")
    CadastroResponse toCadastroResponse(Cadastro cadastro);

    CadastroMessageResponse toCadastroMessageResponse(String message);
}
