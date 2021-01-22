package com.github.charlesluxinger.bytebank.domain.model.exeception;

import java.math.BigDecimal;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
public class GreaterThanDepositValueException extends IllegalStateException {

    public static final String EXISTS_ERROR_MESSAGE = "The value: %s is greater than 2000.0.";

    public GreaterThanDepositValueException(final BigDecimal value) {
        super(String.format(EXISTS_ERROR_MESSAGE, value.toString()));
    }

}
