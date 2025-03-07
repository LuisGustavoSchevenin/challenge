package br.com.challenge.infrastructure.adapter.persistence.mapper;

import br.com.challenge.infrastructure.adapter.in.web.dto.EmailNotification;
import br.com.challenge.infrastructure.adapter.exception.JsonParseEmailException;
import br.com.challenge.domain.model.Cadastro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class EmailMapper {

    private final ObjectMapper objectMapper;

    public EmailMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public EmailNotification toEmailNotification(final String subject, final String message, final Cadastro cadastro) {
        try {
            String body = objectMapper.writeValueAsString(cadastro);
            return new EmailNotification(subject, message, body);
        } catch (JsonProcessingException e) {
            throw new JsonParseEmailException("Error trying to parse the email content");
        }
    }
}
