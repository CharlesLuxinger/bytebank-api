package com.github.charlesluxinger.bytebank.controller;


import com.github.charlesluxinger.bytebank.infra.model.AccountDocument;
import com.github.charlesluxinger.bytebank.infra.repository.AccountRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import java.math.BigDecimal;

/**
 * @author Charles Luxinger
 * @version 1.0.0 21/01/21
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseClassControllerTest {

    @Autowired
    AccountRepository repository;

    @Autowired
    ReactiveMongoTemplate template;

    @LocalServerPort
    private int port;

    protected static final String JOAO_OWNER_NAME = "João Manuel";
    protected static final String JOAO_DOCUMENT = "999.999.999-98";

    protected static final String MANUEL_OWNER_NAME = "Manuel João";
    protected static final String MANUEL_DOCUMENT = "999.999.999-99";

    @BeforeEach
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.basePath = "api/v1/account";
        RestAssured.port = port;

        repository.deleteAll().block();
    }

    protected String stubAccount(String name, String document) {
        return repository
                .save(accountBuilder(name, document))
                .map(AccountDocument::getId)
                .flatMap(id -> repository.deposit(id, BigDecimal.TEN).thenReturn(id))
                .block();
    }

    protected AccountDocument accountBuilder(String name, String document) {
        return AccountDocument.builder().ownerName(name).document(document).build();
    }

}