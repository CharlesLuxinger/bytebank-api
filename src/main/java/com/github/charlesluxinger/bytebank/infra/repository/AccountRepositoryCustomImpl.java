package com.github.charlesluxinger.bytebank.infra.repository;

import com.github.charlesluxinger.bytebank.infra.model.AccountDocument;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static com.github.charlesluxinger.bytebank.infra.model.AccountDocument.BALANCE;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@AllArgsConstructor
public class AccountRepositoryCustomImpl implements AccountRepositoryCustom {

    public static final String ID = "_id";
    private final ReactiveMongoTemplate template;

    @Override
    public Mono<Void> deposit(final String document, final BigDecimal value) {
        return template
                .updateFirst(query(where(ID).is(document)), update(ID, document).inc(BALANCE, value), AccountDocument.class)
                .then();
    }

}
