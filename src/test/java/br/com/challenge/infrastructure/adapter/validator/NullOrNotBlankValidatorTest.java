package br.com.challenge.infrastructure.adapter.validator;

import br.com.challenge.infrastructure.adapter.validator.NullOrNotBlankValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NullOrNotBlankValidatorTest {

    private final NullOrNotBlankValidator nullOrNotBlankValidator = new NullOrNotBlankValidator();

    @Test
    void shouldValidateNullOrNotBlank() {
        assertFalse(nullOrNotBlankValidator.isValid("", null));
        assertTrue(nullOrNotBlankValidator.isValid(null, null));
        assertTrue(nullOrNotBlankValidator.isValid("OK", null));
    }
}