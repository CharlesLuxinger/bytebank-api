package com.github.charlesluxinger.bytebank.domain.model.exeception;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
public class AccountDuplicatedException extends IllegalStateException {

    public static final String EXISTS_ERROR_MESSAGE = "Account with document '%s' already exists.";

    public AccountDuplicatedException(final String document) {
        super(String.format(EXISTS_ERROR_MESSAGE, document));
    }

}
