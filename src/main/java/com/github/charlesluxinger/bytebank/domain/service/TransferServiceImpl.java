package com.github.charlesluxinger.bytebank.domain.service;

import com.github.charlesluxinger.bytebank.domain.model.Account;
import com.github.charlesluxinger.bytebank.domain.model.Transfer;
import com.github.charlesluxinger.bytebank.domain.model.exeception.InsufficientBalanceAmountException;
import com.github.charlesluxinger.bytebank.domain.model.exeception.NonPositiveValueException;
import com.github.charlesluxinger.bytebank.domain.model.exeception.TransferException;
import com.github.charlesluxinger.bytebank.infra.model.AccountDocument;
import com.github.charlesluxinger.bytebank.infra.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.webjars.NotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.github.charlesluxinger.bytebank.utils.ExceptionUtils.isEquals;
import static com.github.charlesluxinger.bytebank.utils.NumberUtils.isNegativeOrZero;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@Service
@Validated
@AllArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final AccountRepository repository;

    @Override
    public Mono<Void> transfer(@Valid @NotNull final Transfer transfer) {
        if (isNegativeOrZero(transfer.getValue())) {
            return Mono.error(new NonPositiveValueException(transfer.getValue()));
        }

        return repository
                .findAllById(Set.of(transfer.getAccountTargetId(), transfer.getAccountSourceId()))
                .map(AccountDocument::toDomain)
                .switchIfEmpty(Mono.error(new NotFoundException("Accounts not founded.")))
                .collectList()
                .flatMap(accounts -> transfer(accounts, transfer))
                .onErrorMap(this::errorMap);
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
        return findAccount(accounts, transfer.getAccountTargetId())
                .map(a -> a.balanceIncrement(transfer.getValue()))
                .orElseThrow(() -> new NotFoundException("Target Account not founded."));
    }

    private Account decrementSourceBalance(final List<Account> accounts, final Transfer transfer) {
        return findAccount(accounts, transfer.getAccountSourceId())
                .map(a -> a.balanceDecrement(transfer.getValue()))
                .orElseThrow(() -> new NotFoundException("Source Account not founded."));
    }

    private Optional<Account> findAccount(final List<Account> accounts, final String document) {
        return accounts
                .stream()
                .filter(a -> a.getId().equals(document))
                .findAny();
    }

    private Throwable errorMap(final Throwable err) {
        return isEquals(err, NotFoundException.class) || isEquals(err, InsufficientBalanceAmountException.class) ?
                new TransferException(err.getLocalizedMessage()) :
                err;
    }

}
