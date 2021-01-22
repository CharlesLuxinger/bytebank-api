package com.github.charlesluxinger.bytebank.controller.model.request;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.github.charlesluxinger.bytebank.BaseClassTest.MANUEL_DOCUMENT;
import static com.github.charlesluxinger.bytebank.BaseClassTest.MANUEL_OWNER_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AccountRequestTest {

    @Test
    void should_return_an_account_domain() {
        var accountRequest = AccountRequest.builder().ownerName(MANUEL_OWNER_NAME).document(MANUEL_DOCUMENT).build();
        var accountDomain = accountRequest.toDomain();

        assertNull(accountDomain.getId());
        assertEquals(accountDomain.getBalance(), BigDecimal.ZERO);
        assertEquals(accountRequest.getOwnerName(), accountDomain.getOwnerName());
        assertEquals(accountRequest.getDocument(), accountDomain.getDocument());
    }
}