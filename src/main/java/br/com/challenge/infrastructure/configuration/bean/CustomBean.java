package br.com.challenge.infrastructure.configuration.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

import static br.com.challenge.utils.DateTimeUtils.DATE_TIME_FORMATTER_PATTERN;

@Configuration
public class CustomBean {

    @Bean
    public ObjectMapper objectMapper() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER_PATTERN));

        return new ObjectMapper()
                .registerModule(javaTimeModule)
                .findAndRegisterModules();
    }
}
