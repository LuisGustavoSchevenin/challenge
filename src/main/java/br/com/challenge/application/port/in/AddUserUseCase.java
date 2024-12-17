package br.com.challenge.application.port.in;

import br.com.challenge.adapter.dto.UserRequest;
import br.com.challenge.domain.model.User;

public interface AddUserUseCase {

    User createUser(UserRequest userRequest);
}
