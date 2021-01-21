package com.github.charlesluxinger.bytebank.infra.repository;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
public interface AccountRepositoryCustom {

    Mono<Void> deposit(final String id, final BigDecimal value);

}
