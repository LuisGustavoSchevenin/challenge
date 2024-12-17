package br.com.challenge.adapter.in.rest;

import br.com.challenge.adapter.dto.UserRequest;
import br.com.challenge.adapter.dto.UserResponse;
import br.com.challenge.application.port.in.AddUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserResource {

    private final AddUserUseCase addUserService;

    public UserResource(AddUserUseCase addUserService) {
        this.addUserService = addUserService;
    }

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> createUser(@RequestBody final UserRequest userRequest) {
        addUserService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserResponse("", "", 20, "", "", ""));
    }
}
