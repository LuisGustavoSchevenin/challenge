package br.com.challenge.adapter.exception;

import br.com.challenge.adapter.dto.Error;
import br.com.challenge.adapter.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        LOG.error("The MethodArgumentNotValidException was thrown: {}", ex.getMessage());

        List<FieldError> fieldErrors = ex.getFieldErrors();
        List<Error> errors = new ArrayList<>();

        fieldErrors.forEach(fieldError -> {
            String fieldName = fieldError.getField().replace("Request", "");
            String errorMessage = fieldError.getDefaultMessage();

            errors.add(new Error(fieldName, errorMessage));
        });

        ErrorResponse errorResponse = new ErrorResponse(ex.getStatusCode().value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errors);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = InvalidUUIDException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUUIDException(final InvalidUUIDException ex) {
        LOG.error("The InvalidUUIDException was thrown: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                Collections.emptyList());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = CadastroAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUUIDException(final CadastroAlreadyExistException ex) {
        LOG.error("The CadastroAlreadyExistException was thrown: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                Collections.emptyList());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = CadastroNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCadastroNotFoundException(final CadastroNotFoundException ex) {
        LOG.error("The CadastroNotFoundException was thrown: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                Collections.emptyList());

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Void> handleInternalServerException(final Exception ex) {
        LOG.error("An unexpected Exception was thrown: {}", ex.getMessage());

        return ResponseEntity.internalServerError().build();
    }
}
