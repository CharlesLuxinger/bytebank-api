package com.github.charlesluxinger.bytebank.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.github.charlesluxinger.bytebank.BaseClassTest.*;
import static com.github.charlesluxinger.bytebank.controller.AccountControllerImpl.ACCOUNT_PATH;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Charles Luxinger
 * @version 1.0.0 21/01/21
 */
class TransferControllerTest extends BaseClassControllerTest {

    private final String BODY = "{\"sourceAccountId\":\"%s\",\"targetAccountId\":\"%s\",\"value\":%s}";

    @Test
    @DisplayName("should return status 200 when transfer with a valid value")
    void should_return_status_200_when_transfer_with_a_valid_value() {
        var targetId = stubAccountId(JOAO_OWNER_NAME, JOAO_DOCUMENT);
        var sourceId = stubAccountId(MANUEL_OWNER_NAME, MANUEL_DOCUMENT);

        given()
                .contentType(ContentType.JSON)
                .body(String.format(BODY ,sourceId, targetId, "1"))
            .expect()
                .statusCode(200)
            .when()
                .patch()
            .then()
                .header("Content-Length", "0");

        var targetAccount = repository.findById(targetId).block();
        assertEquals(BigDecimal.valueOf(11), targetAccount.getBalance());

        var sourceAccount = repository.findById(sourceId).block();
        assertEquals(BigDecimal.valueOf(9), sourceAccount.getBalance());
    }

    @Test
    @DisplayName("should return status 200 when source balance account will be zero after transfer")
    void should_return_status_200_when_source_balance_account_will_be_zero_after_transfer() {
        var targetId = stubAccountId(JOAO_OWNER_NAME, JOAO_DOCUMENT);
        var sourceId = stubAccountId(MANUEL_OWNER_NAME, MANUEL_DOCUMENT);

        given()
                .contentType(ContentType.JSON)
                .body(String.format(BODY ,sourceId, targetId, "10"))
            .expect()
                .statusCode(200)
            .when()
                .patch()
            .then()
                .header("Content-Length", "0");

        var targetAccount = repository.findById(targetId).block();
        assertEquals(BigDecimal.valueOf(20), targetAccount.getBalance());

        var sourceAccount = repository.findById(sourceId).block();
        assertEquals(BigDecimal.ZERO, sourceAccount.getBalance());
    }

    @Test
    @DisplayName("should return status 400 when source balance account will be less zero after transfer")
    void should_return_status_400_when_source_balance_account_will_be_less_zero_after_transfer() {
        var targetId = stubAccountId(JOAO_OWNER_NAME, JOAO_DOCUMENT);
        var sourceId = stubAccountId(MANUEL_OWNER_NAME, MANUEL_DOCUMENT);

        given()
                .contentType(ContentType.JSON)
                .body(String.format(BODY ,sourceId, targetId, "11"))
            .expect()
                .statusCode(400)
                .contentType(ContentType.JSON)
            .when()
                .patch()
            .then()
                .body("status", is(400))
                .body("title", equalTo("Bad Request"))
                .body("detail", equalTo("Insufficient balance to transfer the amount: 11."))
                .body("path", equalTo(ACCOUNT_PATH))
                .body("timestamp", notNullValue());
    }

    @Test
    @DisplayName("should return status 400 when transfer value is negative")
    void should_return_status_400_when_transfer_value_is_negative() {
        var targetId = stubAccountId(JOAO_OWNER_NAME, JOAO_DOCUMENT);
        var sourceId = stubAccountId(MANUEL_OWNER_NAME, MANUEL_DOCUMENT);

        given()
                .contentType(ContentType.JSON)
                .body(String.format(BODY ,sourceId, targetId, "-1"))
            .expect()
                .statusCode(400)
                .contentType(ContentType.JSON)
            .when()
                .patch()
            .then()
                .body("status", is(400))
                .body("title", equalTo("Bad Request"))
                .body("detail", equalTo("Isn't positive value: -1."))
                .body("path", equalTo(ACCOUNT_PATH))
                .body("timestamp", notNullValue());
    }

    @Test
    @DisplayName("should return status 400 when source id & target id is equals")
    void should_return_status_400_when_source_id_and_target_id_is_equals() {
        var id = stubAccountId(JOAO_OWNER_NAME, JOAO_DOCUMENT);

        given()
                .contentType(ContentType.JSON)
                .body(String.format(BODY ,id, id, "1"))
            .expect()
                .statusCode(400)
                .contentType(ContentType.JSON)
            .when()
                .patch()
            .then()
                .body("status", is(400))
                .body("title", equalTo("Bad Request"))
                .body("detail", equalTo("Target Id & Source Id is equals."))
                .body("path", equalTo(ACCOUNT_PATH))
                .body("timestamp", notNullValue());
    }

}