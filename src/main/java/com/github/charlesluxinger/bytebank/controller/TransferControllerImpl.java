package com.github.charlesluxinger.bytebank.controller;

import com.github.charlesluxinger.bytebank.controller.model.request.TransferRequest;
import com.github.charlesluxinger.bytebank.domain.model.exeception.GreaterThanDepositValueException;
import com.github.charlesluxinger.bytebank.domain.model.exeception.NonPositiveValueException;
import com.github.charlesluxinger.bytebank.domain.service.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

import static com.github.charlesluxinger.bytebank.controller.AccountControllerImpl.ACCOUNT_PATH;
import static com.github.charlesluxinger.bytebank.controller.model.exception.ApiExceptionResponse.buildBadRequestResponse;
import static com.github.charlesluxinger.bytebank.utils.ExceptionUtils.isEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@Validated
@RestController
@RequestMapping(path = ACCOUNT_PATH, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class TransferControllerImpl implements TransferController {

    private final TransferService service;

    @Override
    @PatchMapping
    public Mono<ResponseEntity> transfer(@RequestBody @Valid @NotNull final TransferRequest transfer) {
        return service
                .transfer(transfer.toDomain())
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
