package com.github.charlesluxinger.bytebank.controller;

import com.github.charlesluxinger.bytebank.controller.model.request.DepositRequest;
import com.github.charlesluxinger.bytebank.domain.model.exeception.GreaterThanDepositValueException;
import com.github.charlesluxinger.bytebank.domain.model.exeception.NonPositiveValueException;
import com.github.charlesluxinger.bytebank.domain.service.DepositService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URI;

import static com.github.charlesluxinger.bytebank.controller.AccountControllerImpl.ACCOUNT_PATH;
import static com.github.charlesluxinger.bytebank.controller.model.exception.ApiExceptionResponse.buildBadRequestResponse;
import static com.github.charlesluxinger.bytebank.utils.ExceptionUtils.isEquals;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@Validated
@RestController
@RequestMapping(path = ACCOUNT_PATH, consumes = APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class DepositControllerImpl implements DepositController {

    private final DepositService service;

    @Override
    @ResponseStatus(CREATED)
    @PostMapping(value = "/{id}")
    public Mono<ResponseEntity> deposit(@PathVariable @NotBlank final String id,
                                        @RequestBody @Valid @NotNull final DepositRequest deposit) {
        return service
                .deposit(id, deposit.getValue())
                .map($ -> ResponseEntity.created(URI.create(ACCOUNT_PATH)))
                .cast(ResponseEntity.class)
                .onErrorResume(this::errorMap);
    }

    private Mono<ResponseEntity> errorMap(final Throwable err) {
        return isEquals(err, GreaterThanDepositValueException.class) || isEquals(err, NonPositiveValueException.class) ?
                Mono.just(buildBadRequestResponse(ACCOUNT_PATH, err.getLocalizedMessage())) :
                Mono.error(err);
    }

}
