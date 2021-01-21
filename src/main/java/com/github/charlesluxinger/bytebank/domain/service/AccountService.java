package com.github.charlesluxinger.bytebank.domain.service;

import com.github.charlesluxinger.bytebank.domain.model.Account;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
public interface AccountService {

    Mono<Account> insertIfNotExists(@Valid @NotNull final Account account);

}
