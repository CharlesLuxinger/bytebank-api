package com.github.charlesluxinger.bytebank.controller;

import com.github.charlesluxinger.bytebank.controller.model.exception.ApiExceptionResponse;
import com.github.charlesluxinger.bytebank.controller.model.request.DepositRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@Tag(name = "Deposit")
public interface DepositController {

    @Operation(summary = "Deposit in an Account", responses = {
            @ApiResponse(responseCode = "201", description = "Deposited in an Account"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE))
    })
    @Parameter(name = "id", in = PATH, required = true, description = "Account Id", example = "507f1f77bcf86cd799439011")
    Mono<ResponseEntity> deposit(@NotBlank final String id, @Valid @NotNull final DepositRequest deposit);

}
