package com.github.charlesluxinger.bytebank.infra.repository;

import com.github.charlesluxinger.bytebank.infra.model.AccountDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Set;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@Repository
public interface AccountRepository extends AccountRepositoryCustom, ReactiveMongoRepository<AccountDocument, String> {

    Flux<AccountDocument> findAllByDocument(final Set<String> documents);

}
