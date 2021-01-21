package com.github.charlesluxinger.bytebank.domain.service;

import com.github.charlesluxinger.bytebank.domain.model.Account;
import com.github.charlesluxinger.bytebank.domain.model.Transfer;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
public interface AccountService {

    Mono<Account> insertIfNotExists(final Account account);

    Mono<Void> deposit(final String document, final BigDecimal value);

    Mono<Void> transfer(final Transfer transfer);

}
