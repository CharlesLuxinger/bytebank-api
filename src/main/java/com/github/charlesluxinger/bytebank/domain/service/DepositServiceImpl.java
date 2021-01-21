package com.github.charlesluxinger.bytebank.domain.service;

import com.github.charlesluxinger.bytebank.domain.model.exeception.GreaterThanDepositValueException;
import com.github.charlesluxinger.bytebank.domain.model.exeception.NonPositiveValueException;
import com.github.charlesluxinger.bytebank.infra.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

import static com.github.charlesluxinger.bytebank.utils.NumberUtils.isLessOrEqualsTwoThousand;
import static com.github.charlesluxinger.bytebank.utils.NumberUtils.isNegativeOrZero;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@Service
@Validated
@AllArgsConstructor
public class DepositServiceImpl implements DepositService {

    private final AccountRepository repository;

    @Override
    public Mono<Void> deposit(@NotBlank final String id, @NotNull @Positive final BigDecimal value) {
        if (isNegativeOrZero(value)) {
            return Mono.error(new NonPositiveValueException(value));
        }

        if (isLessOrEqualsTwoThousand(value)) {
            return Mono.error(new GreaterThanDepositValueException(value));
        }

        return repository.deposit(id, value);
    }

}
