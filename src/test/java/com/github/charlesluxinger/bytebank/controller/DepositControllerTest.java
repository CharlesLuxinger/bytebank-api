package com.github.charlesluxinger.bytebank.controller;

import com.github.charlesluxinger.bytebank.infra.model.AccountDocument;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.charlesluxinger.bytebank.BaseClassTest.*;
import static com.github.charlesluxinger.bytebank.controller.AccountControllerImpl.ACCOUNT_PATH;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * @author Charles Luxinger
 * @version 1.0.0 21/01/21
 */
class DepositControllerTest extends BaseClassControllerTest {

    private final String BODY = "{\"value\":%s}";

    @Test
    @DisplayName("should return status 201 when deposit with a valid value")
    void should_return_status_201_when_deposit_with_a_valid_value() {
        var id = repository
                .save(accountDocumentBuilder(MANUEL_OWNER_NAME, MANUEL_DOCUMENT))
                .map(AccountDocument::getId)
                .block();

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(String.format(BODY,"1000"))
            .expect()
                .statusCode(201)
            .when()
                .post("/{id}")
            .then()
                .header("Content-Length", "0");
    }

    @Test
    @DisplayName("should return status 400 when deposit value is greater than two thousand")
    void should_return_status_400_when_deposit_value_is_greater_than_two_thousand() {
        var id = repository
                .save(accountDocumentBuilder(MANUEL_OWNER_NAME, MANUEL_DOCUMENT))
                .map(AccountDocument::getId)
                .block();

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(String.format(BODY, "2000.1"))
            .expect()
                .statusCode(400)
                .contentType(ContentType.JSON)
            .when()
                .post("/{id}")
            .then()
                .body("status", is(400))
                .body("title", equalTo("Bad Request"))
                .body("detail", equalTo("The value: 2000.1 is greater than 2000.0."))
                .body("path", equalTo(ACCOUNT_PATH))
                .body("timestamp", notNullValue());
    }

    @Test
    @DisplayName("should return status 400 when deposit value is zero")
    void should_return_status_400_when_deposit_value_is_zero() {
        var id = repository
                .save(accountDocumentBuilder(MANUEL_OWNER_NAME, MANUEL_DOCUMENT))
                .map(AccountDocument::getId)
                .block();

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(String.format(BODY, "0"))
            .expect()
                .statusCode(400)
                .contentType(ContentType.JSON)
            .when()
                .post("/{id}")
            .then()
                .body("status", is(400))
                .body("title", equalTo("Bad Request"))
                .body("detail", equalTo("Isn't positive value: 0."))
                .body("path", equalTo(ACCOUNT_PATH))
                .body("timestamp", notNullValue());
    }

    @Test
    @DisplayName("should return status 400 when deposit value is negative")
    void should_return_status_400_when_deposit_value_is_negative() {
        var id = repository
                .save(accountDocumentBuilder(MANUEL_OWNER_NAME, MANUEL_DOCUMENT))
                .map(AccountDocument::getId)
                .block();

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(String.format(BODY, "-1"))
            .expect()
                .statusCode(400)
                .contentType(ContentType.JSON)
            .when()
                .post("/{id}")
            .then()
                .body("status", is(400))
                .body("title", equalTo("Bad Request"))
                .body("detail", equalTo("Isn't positive value: -1."))
                .body("path", equalTo(ACCOUNT_PATH))
                .body("timestamp", notNullValue());
    }

}