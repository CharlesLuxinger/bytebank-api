package com.github.charlesluxinger.bytebank.controller;

import com.github.charlesluxinger.bytebank.controller.model.request.DepositRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@Tag(name = "Deposit")
public interface DepositController {

    @Operation(summary = "Deposit in an Account", responses = {
            @ApiResponse(responseCode = "201", description = "Deposited in an Account",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE))
    })
    @Parameter(in = PATH, required = true, description = "User CPF document", example = "999.999.999-99")
    Mono<ResponseEntity> deposit(final String document, final DepositRequest deposit);

}
