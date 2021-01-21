package com.github.charlesluxinger.bytebank.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@EnableReactiveMongoRepositories(basePackages = "com.github.charlesluxinger.bytebank.infra.repository")
public class MongoDBConfig extends AbstractReactiveMongoConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Override
    protected String getDatabaseName() {
        return database;
    }

}
