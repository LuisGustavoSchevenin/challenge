package br.com.challenge.adapter.validator;

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