package com.github.charlesluxinger.bytebank.domain.service;

import com.github.charlesluxinger.bytebank.domain.model.exeception.AccountDuplicatedException;
import com.github.charlesluxinger.bytebank.infra.model.AccountDocument;
import com.github.charlesluxinger.bytebank.infra.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.github.charlesluxinger.bytebank.BaseClassTest.*;
import static org.mockito.Mockito.when;


/**
 * @author Charles Luxinger
 * @version 24/01/21
 */
@Import(AccountServiceImpl.class)
@ExtendWith(SpringExtension.class)
class AccountServiceTest {

    @Autowired
    private AccountService service;

    @MockBean
    private AccountRepository repository;

    @Test
    @DisplayName("should throw exceptions when insert an exists document")
    void should_throw_exceptions_when_insert_an_exists_document() {
        when(repository.insert((AccountDocument) Mockito.any())).thenReturn(Mono.error(new DuplicateKeyException("")));

        StepVerifier
                .create(service.insertIfNotExists(accountDocumentBuilder(MANUEL_OWNER_NAME, MANUEL_DOCUMENT).toDomain()))
                .expectError(AccountDuplicatedException.class)
                .verify();
    }

    @Test
    @DisplayName("should return a mono error when error throed")
    void should_a_mono_error_when_error_throed() {
        when(repository.insert((AccountDocument) Mockito.any())).thenReturn(Mono.error(new RuntimeException()));

        StepVerifier
                .create(service.insertIfNotExists(accountDocumentBuilder(MANUEL_OWNER_NAME, MANUEL_DOCUMENT).toDomain()))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    @DisplayName("should insert when not exists")
    void should_insert_when_not_exists() {
        var document = accountDocumentBuilder(MANUEL_OWNER_NAME, MANUEL_DOCUMENT);

        when(repository.insert((AccountDocument) Mockito.any())).thenReturn(Mono.just(document));

        var mono = service.insertIfNotExists(document.toDomain());

        StepVerifier
                .create(mono)
                .expectNext(document.toDomain())
                .verifyComplete();
    }


}