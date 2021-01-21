package com.github.charlesluxinger.bytebank.controller;

import com.github.charlesluxinger.bytebank.controller.model.request.DepositRequest;
import com.github.charlesluxinger.bytebank.domain.model.exeception.NonPositiveValueException;
import com.github.charlesluxinger.bytebank.domain.service.DepositService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URI;

import static com.github.charlesluxinger.bytebank.controller.AccountControllerImpl.ACCOUNT_PATH;
import static com.github.charlesluxinger.bytebank.controller.model.exception.ApiExceptionResponse.buildBadRequestResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@Validated
@RestController
@RequestMapping(value = ACCOUNT_PATH, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class DepositControllerImpl implements DepositController {

    private final DepositService service;

    @Override
    @PostMapping(value = "/{id}")
    public Mono<ResponseEntity> deposit(@RequestParam @NotBlank final String id, @NotNull final DepositRequest deposit) {
        return service
                .deposit(id, deposit.getValue())
                .map($ -> ResponseEntity.created(URI.create(ACCOUNT_PATH)))
                .cast(ResponseEntity.class)
                .onErrorResume(err -> mapError(err, ACCOUNT_PATH, NonPositiveValueException.class));
    }

    private Mono<ResponseEntity> mapError(final Throwable err, final String path , final Class<? extends RuntimeException> clazz) {
        return err.getClass() != clazz ? Mono.error(err) : Mono.just(buildBadRequestResponse(path, err.getLocalizedMessage()));
    }

}
