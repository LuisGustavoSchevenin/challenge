package br.com.challenge.adapter.exception;

import br.com.challenge.adapter.dto.Error;
import br.com.challenge.adapter.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
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
}
