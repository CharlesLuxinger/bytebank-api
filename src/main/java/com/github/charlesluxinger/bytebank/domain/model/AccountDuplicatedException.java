package com.github.charlesluxinger.bytebank.domain.model;

public class AccountDuplicatedException extends IllegalStateException {

    public static final String EXISTS_ERROR_MESSAGE = "Account with document '#%s' already exists.";

    public AccountDuplicatedException(final String document) {
        super(String.format(EXISTS_ERROR_MESSAGE, document));
    }

}
