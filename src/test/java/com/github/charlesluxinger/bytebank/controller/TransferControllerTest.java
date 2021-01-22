package com.github.charlesluxinger.bytebank.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.github.charlesluxinger.bytebank.controller.AccountControllerImpl.ACCOUNT_PATH;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Charles Luxinger
 * @version 1.0.0 21/01/21
 */
class TransferControllerTest extends BaseClassControllerTest {

    private final String BODY = "{\"accountSourceId\":\"%s\",\"accountTargetId\":\"%s\",\"value\":%s}";

    @Test
    @DisplayName("should return status 200 when transfer with a valid value")
    void should_return_status_200_when_transfer_with_a_valid_value() {
        var targetId = stubAccount(JOAO_OWNER_NAME, JOAO_DOCUMENT);
        var sourceId = stubAccount(MANUEL_OWNER_NAME, MANUEL_DOCUMENT);

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
        var targetId = stubAccount(JOAO_OWNER_NAME, JOAO_DOCUMENT);
        var sourceId = stubAccount(MANUEL_OWNER_NAME, MANUEL_DOCUMENT);

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
        var targetId = stubAccount(JOAO_OWNER_NAME, JOAO_DOCUMENT);
        var sourceId = stubAccount(MANUEL_OWNER_NAME, MANUEL_DOCUMENT);

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

}