package com.github.charlesluxinger.bytebank.infra.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.github.charlesluxinger.bytebank.BaseClassTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountDocumentTest {

    @Test
    @DisplayName("should an account document of domain")
    void should_return_an_account_document_of_domain() {
        var accountDomain = accountBuilder(MANUEL_OWNER_NAME, MANUEL_DOCUMENT);
        var accountDocument = AccountDocument.of(accountDomain);

        assertEquals(accountDomain.getBalance(),accountDocument.getBalance());
        assertEquals(accountDomain.getDocument(),accountDocument.getDocument());
        assertEquals(accountDomain.getOwnerName(),accountDocument.getOwnerName());
        assertEquals(accountDomain.getId(),accountDocument.getId());
    }

    @Test
    @DisplayName("should an account domain of document")
    void should_return_an_account_domain_of_document() {
        var accountDocument = AccountDocument.builder().ownerName(MANUEL_OWNER_NAME).document(MANUEL_DOCUMENT).balance(BigDecimal.TEN).build();
        var accountDomain = accountDocument.toDomain();

        assertEquals(accountDocument.getBalance(),accountDomain.getBalance());
        assertEquals(accountDocument.getDocument(),accountDomain.getDocument());
        assertEquals(accountDocument.getOwnerName(),accountDomain.getOwnerName());
        assertEquals(accountDocument.getId(),accountDomain.getId());
    }

}