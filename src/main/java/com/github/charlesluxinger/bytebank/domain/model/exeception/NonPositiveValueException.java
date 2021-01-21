package com.github.charlesluxinger.bytebank.domain.model.exeception;

import java.math.BigDecimal;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
public class NonPositiveValueException extends IllegalStateException {

    public static final String EXISTS_ERROR_MESSAGE = "Isn't positive value: %s.";

    public NonPositiveValueException(final BigDecimal value) {
        super(String.format(EXISTS_ERROR_MESSAGE, value.toString()));
    }

}
