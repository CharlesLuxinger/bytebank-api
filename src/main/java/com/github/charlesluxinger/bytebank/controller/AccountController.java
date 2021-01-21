package com.github.charlesluxinger.bytebank.controller;

import com.github.charlesluxinger.bytebank.controller.model.request.AccountRequest;
import com.github.charlesluxinger.bytebank.controller.model.response.AccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@Tag(name = "Account")
public interface AccountController {

    @Operation(summary = "Save an Account", responses = {
            @ApiResponse(responseCode = "201", description = "Created an Account",  content = @Content(
                    schema =  @Schema(implementation = AccountResponse.class), mediaType = APPLICATION_JSON_VALUE))
    })
    Mono<ResponseEntity> save(final AccountRequest account);

}
