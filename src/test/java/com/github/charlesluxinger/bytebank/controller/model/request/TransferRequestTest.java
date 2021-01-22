package com.github.charlesluxinger.bytebank.controller.model.request;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferRequestTest {

    @Test
    void should_return_an_transfer_domain() {
        var transferRequest = TransferRequest.builder().accountSourceId("123").accountTargetId("321").value(BigDecimal.TEN).build();
        var transferDomain = transferRequest.toDomain();

        assertEquals(transferRequest.getAccountSourceId(), transferDomain.getAccountSourceId());
        assertEquals(transferRequest.getAccountTargetId(), transferDomain.getAccountTargetId());
        assertEquals(transferRequest.getValue(), transferDomain.getValue());
    }

}