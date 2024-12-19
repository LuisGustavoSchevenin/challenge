package br.com.challenge.adapter.out.mapper;

import br.com.challenge.adapter.dto.CadastroMessageResponse;
import br.com.challenge.adapter.dto.CadastroRequest;
import br.com.challenge.adapter.dto.CadastroDoneResponse;
import br.com.challenge.adapter.out.persistence.CadastroEntity;
import br.com.challenge.domain.model.Cadastro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CadastroMapper {

    @Mapping(target = "cpf", expression = "java( br.com.challenge.utils.StringUtils.removeSpecialCharacters(cadastroRequest.cpf()))")
    Cadastro toCadastro(CadastroRequest cadastroRequest);

    @Mapping(target = "id", ignore = true)
    CadastroEntity toCadastroEntity(Cadastro cadastro);

    CadastroDoneResponse toCadastroResponse(Cadastro cadastro);

    CadastroMessageResponse toCadastroMessageResponse(String message);
}
