package com.github.charlesluxinger.bytebank.domain.model.exeception;

public class TransferException extends RuntimeException {

    public TransferException(final String message) {
        super(message);
    }

}
