package com.github.charlesluxinger.bytebank.domain.service;

import com.github.charlesluxinger.bytebank.domain.model.Account;
import com.github.charlesluxinger.bytebank.domain.model.exeception.AccountDuplicatedException;
import com.github.charlesluxinger.bytebank.infra.model.AccountDocument;
import com.github.charlesluxinger.bytebank.infra.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.github.charlesluxinger.bytebank.utils.ExceptionUtils.isEquals;

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
                .onErrorResume(e -> insertIfNotExistsErrorMap(account, e));
    }

    private Mono<Account> insertIfNotExistsErrorMap(final Account partners, final Throwable err) {
        return isEquals(err, DuplicateKeyException.class) ?
                Mono.error(new AccountDuplicatedException(partners.getDocument())) :
                Mono.error(err);
    }

}
