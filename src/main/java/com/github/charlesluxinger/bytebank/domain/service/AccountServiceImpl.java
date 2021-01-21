package com.github.charlesluxinger.bytebank.domain.service;

import com.github.charlesluxinger.bytebank.domain.model.Account;
import com.github.charlesluxinger.bytebank.domain.model.exeception.AccountDuplicatedException;
import com.github.charlesluxinger.bytebank.domain.model.exeception.NonPositiveValueException;
import com.github.charlesluxinger.bytebank.infra.model.AccountDocument;
import com.github.charlesluxinger.bytebank.infra.repository.AccountRepository;
import com.mongodb.DuplicateKeyException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@Service
@Validated
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Override
    public Mono<Account> insertIfNotExists(@Valid @NotNull final Account account) {
        return repository
                .insert(AccountDocument.of(account))
                .map(AccountDocument::toDomain)
                .onErrorMap(e -> errorMap(account, e));
    }

    @Override
    public Mono<Void> deposit(@NotBlank final String document, @NotNull @Positive final BigDecimal value) {
        if (isNegativeOrZero(value)) {
            return Mono.error(new NonPositiveValueException(value));
        }

        return repository.deposit(document, value);
    }

    private boolean isNegativeOrZero(BigDecimal value) {
        return value.compareTo(ZERO) > 0;
    }

    private Throwable errorMap(final Account partners, final Throwable err) {
        return err.getClass().equals(DuplicateKeyException.class) ?
                new AccountDuplicatedException(partners.getDocument()) :
                err;
    }

}
