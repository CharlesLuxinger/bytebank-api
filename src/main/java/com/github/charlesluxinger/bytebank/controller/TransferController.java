package com.github.charlesluxinger.bytebank.controller;

import com.github.charlesluxinger.bytebank.controller.model.exception.ApiExceptionResponse;
import com.github.charlesluxinger.bytebank.controller.model.request.TransferRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@Tag(name = "Transfer")
public interface TransferController {

    @Operation(summary = "Transfer between Accounts", responses = {
            @ApiResponse(responseCode = "201", description = "Transferred successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE))
    })
    Mono<ResponseEntity> transfer(@Valid @NotNull final TransferRequest transfer);

}
