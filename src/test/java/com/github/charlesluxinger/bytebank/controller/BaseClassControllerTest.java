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

import static com.github.charlesluxinger.bytebank.BaseClassTest.accountDocumentBuilder;

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

    @BeforeEach
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.basePath = "api/v1/account";
        RestAssured.port = port;

        repository.deleteAll().block();
    }

    protected String stubAccountId(String name, String document) {
        return repository
                .save(accountDocumentBuilder(name, document))
                .map(AccountDocument::getId)
                .flatMap(id -> repository.deposit(id, BigDecimal.TEN).thenReturn(id))
                .block();
    }

}