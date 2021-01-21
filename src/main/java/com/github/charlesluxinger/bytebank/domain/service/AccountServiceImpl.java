package com.github.charlesluxinger.bytebank.domain.service;

import com.github.charlesluxinger.bytebank.domain.model.Account;
import com.github.charlesluxinger.bytebank.domain.model.Transfer;
import com.github.charlesluxinger.bytebank.domain.model.exeception.TransferException;
import com.github.charlesluxinger.bytebank.domain.model.exeception.AccountDuplicatedException;
import com.github.charlesluxinger.bytebank.domain.model.exeception.GreaterThanDepositValueException;
import com.github.charlesluxinger.bytebank.domain.model.exeception.NonPositiveValueException;
import com.github.charlesluxinger.bytebank.infra.model.AccountDocument;
import com.github.charlesluxinger.bytebank.infra.repository.AccountRepository;
import com.mongodb.DuplicateKeyException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.webjars.NotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.github.charlesluxinger.bytebank.utils.ExceptionUtils.isEquals;
import static com.github.charlesluxinger.bytebank.utils.NumberUtils.isLessOrEqualsTwoThousand;
import static com.github.charlesluxinger.bytebank.utils.NumberUtils.isNegativeOrZero;

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
                .onErrorMap(e -> insertIfNotExistsErrorMap(account, e));
    }

    @Override
    public Mono<Void> deposit(@NotBlank final String document, @NotNull @Positive final BigDecimal value) {
        if (isNegativeOrZero(value)) {
            return Mono.error(new NonPositiveValueException(value));
        }

        if (isLessOrEqualsTwoThousand(value)) {
            return Mono.error(new GreaterThanDepositValueException(value));
        }

        return repository.deposit(document, value);
    }

    @Override
    public Mono<Void> transfer(@Valid @NotNull final Transfer transfer) {
        if (isNegativeOrZero(transfer.getValue())) {
            return Mono.error(new NonPositiveValueException(transfer.getValue()));
        }

        return repository
                .findAllByDocument(Set.of(transfer.getDocumentTarget(), transfer.getDocumentSource()))
                .map(AccountDocument::toDomain)
                .switchIfEmpty(Mono.error(new NotFoundException("Accounts not founded.")))
                .collectList()
                .flatMap(accounts -> transfer(accounts, transfer))
                .onErrorMap(this::transferErrorMap);
    }

    private Mono<Void> transfer(final List<Account> accounts, final Transfer transfer) {
        var sourceAccount = decrementSourceBalance(accounts, transfer);
        var targetAccount = incrementTargetBalance(accounts, transfer);

        return Flux
                .just(targetAccount, sourceAccount)
                .map(AccountDocument::of)
                .collectList()
                .flatMapMany(repository::saveAll)
                .then();
    }

    private Account incrementTargetBalance(final List<Account> accounts, final Transfer transfer) {
        return findAccount(accounts, transfer.getDocumentTarget())
                .map(a -> a.balanceIncrement(transfer.getValue()))
                .orElseThrow(() -> new NotFoundException("Target Account not founded."));
    }

    private Optional<Account> findAccount(final List<Account> accounts, final String document) {
        return accounts
                .stream()
                .filter(a -> a.getDocument().equals(document))
                .findAny();
    }

    private Account decrementSourceBalance(final List<Account> accounts, final Transfer transfer) {
        return findAccount(accounts, transfer.getDocumentSource())
                .map(a -> a.balanceDecrement(transfer.getValue()))
                .orElseThrow(() -> new NotFoundException("Source Account not founded."));
    }

    private Throwable insertIfNotExistsErrorMap(final Account partners, final Throwable err) {
        return isEquals(err, DuplicateKeyException.class) ?
                new AccountDuplicatedException(partners.getDocument()) :
                err;
    }

    private Throwable transferErrorMap(final Throwable err) {
        return isEquals(err, NotFoundException.class) || isEquals(err, NonPositiveValueException.class) ?
                new TransferException(err.getLocalizedMessage()) :
                err;
    }

}
