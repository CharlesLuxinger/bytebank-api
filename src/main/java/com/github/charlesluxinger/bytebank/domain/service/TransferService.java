package com.github.charlesluxinger.bytebank.domain.service;

import com.github.charlesluxinger.bytebank.domain.model.Transfer;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
public interface TransferService {

    Mono<Void> transfer(@Valid @NotNull final Transfer transfer);

}
