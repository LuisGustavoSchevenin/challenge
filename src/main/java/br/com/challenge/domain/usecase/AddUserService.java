package br.com.challenge.domain.usecase;

import br.com.challenge.adapter.dto.UserRequest;
import br.com.challenge.application.port.in.AddUserUseCase;
import br.com.challenge.domain.model.User;

public class AddUserService implements AddUserUseCase {

    @Override
    public User createUser(UserRequest userRequest) {
        return null;
    }
}
