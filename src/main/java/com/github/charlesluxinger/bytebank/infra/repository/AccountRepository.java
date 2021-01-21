package com.github.charlesluxinger.bytebank.infra.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@Repository
public interface AccountRepository extends ReactiveMongoRepository<AccountRepository, String> {}
