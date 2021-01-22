package com.github.charlesluxinger.bytebank.domain.service;

import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
public interface DepositService {

    Mono<Void> deposit(@NotBlank final String id, @NotNull final BigDecimal value);

}
