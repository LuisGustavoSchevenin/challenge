package br.com.challenge.configuration;

import br.com.challenge.application.port.in.AddUserUseCase;
import br.com.challenge.domain.usecase.AddUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomBeanConfiguration {

    @Bean(name = "addUserService")
    public AddUserUseCase addUserService() {
        return new AddUserService();
    }


}
