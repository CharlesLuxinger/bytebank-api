package com.github.charlesluxinger.bytebank.controller;

import com.github.charlesluxinger.bytebank.controller.model.request.AccountRequest;
import com.github.charlesluxinger.bytebank.controller.model.response.AccountResponse;
import com.github.charlesluxinger.bytebank.domain.model.exeception.AccountDuplicatedException;
import com.github.charlesluxinger.bytebank.domain.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

import static com.github.charlesluxinger.bytebank.controller.AccountControllerImpl.ACCOUNT_PATH;
import static com.github.charlesluxinger.bytebank.controller.model.exception.ApiExceptionResponse.buildBadRequestResponse;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@Validated
@RestController
@RequestMapping(value = ACCOUNT_PATH, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AccountControllerImpl implements AccountController {

    public static final String ACCOUNT_PATH = "/account";
    private final AccountService service;

    @Override
    @ResponseStatus(CREATED)
    @PostMapping
    public Mono<ResponseEntity> save(@RequestBody @Valid @NotNull final AccountRequest account) {
        return Mono
                .just(account)
                .map(AccountRequest::toDomain)
                .flatMap(service::insertIfNotExists)
                .map(p -> ResponseEntity.created(URI.create(ACCOUNT_PATH)).body(AccountResponse.of(p)))
                .cast(ResponseEntity.class)
                .onErrorResume(err -> errorMap(err, ACCOUNT_PATH, AccountDuplicatedException.class));
    }

    private Mono<ResponseEntity> errorMap(final Throwable err, final String path , final Class<? extends RuntimeException> clazz) {
        return err.getClass() != clazz ? Mono.error(err) : Mono.just(buildBadRequestResponse(path, err.getLocalizedMessage()));
    }

}
