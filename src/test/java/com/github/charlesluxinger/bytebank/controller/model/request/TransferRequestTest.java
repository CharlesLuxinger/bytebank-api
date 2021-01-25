package com.github.charlesluxinger.bytebank.controller.model.request;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferRequestTest {

    @Test
    void should_return_an_transfer_domain() {
        var transferRequest = TransferRequest.builder().sourceAccountId("123").targetAccountId("321").value(BigDecimal.TEN).build();
        var transferDomain = transferRequest.toDomain();

        assertEquals(transferRequest.getSourceAccountId(), transferDomain.getAccountSourceId());
        assertEquals(transferRequest.getTargetAccountId(), transferDomain.getAccountTargetId());
        assertEquals(transferRequest.getValue(), transferDomain.getValue());
    }

}