package com.github.charlesluxinger.bytebank.controller;


import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.index.Index;

import static com.github.charlesluxinger.bytebank.controller.AccountControllerImpl.ACCOUNT_PATH;
import static com.github.charlesluxinger.bytebank.infra.model.AccountDocument.ACCOUNT_DOCUMENT;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.data.domain.Sort.Direction.ASC;

/**
 * @author Charles Luxinger
 * @version 1.0.0 21/01/21
 */
class AccountControllerTest extends BaseClassControllerTest {

    private final String BODY = "{\"ownerName\":\"" + MANUEL_OWNER_NAME + "\",\"document\":\"" + MANUEL_DOCUMENT + "\"}";

    @Test
    @DisplayName("should return status 201 when create a new user")
    void should_return_status_201_when_create_a_new_user() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(BODY)
            .expect()
                .statusCode(201)
            .when()
                .post()
            .then()
                .body("id", is(not(emptyOrNullString())))
                .body("ownerName", equalTo(MANUEL_OWNER_NAME))
                .body("document", equalTo(MANUEL_DOCUMENT));
    }

    @Test
    @DisplayName("should return status 400 when save an exists user")
    void should_return_status_400_when_save_an_exists_user() {
        template.indexOps(ACCOUNT_DOCUMENT)
                .ensureIndex(new Index(MANUEL_DOCUMENT, ASC).unique())
                .flatMap($ -> repository.save(accountBuilder(MANUEL_OWNER_NAME, MANUEL_DOCUMENT)))
                .block();

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(BODY)
            .expect()
                .statusCode(400)
            .when()
                .post()
            .then()
                .body("status", is(400))
                .body("title", equalTo("Bad Request"))
                .body("detail", equalTo("Account with document '999.999.999-99' already exists."))
                .body("path", equalTo(ACCOUNT_PATH))
                .body("timestamp", notNullValue());
    }

}