package com.github.charlesluxinger.bytebank.domain.model.exeception;

import java.math.BigDecimal;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
public class InsufficientBalanceAmountException extends IllegalStateException {

    public static final String EXISTS_ERROR_MESSAGE = "Insufficient balance to transfer the amount: %s.";

    public InsufficientBalanceAmountException(final BigDecimal value) {
        super(String.format(EXISTS_ERROR_MESSAGE, value.toString()));
    }

}
