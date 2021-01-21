package com.github.charlesluxinger.bytebank.infra.config;

import org.bson.types.Decimal128;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Charles Luxinger
 * @version 1.0.0 20/01/21
 */
@EnableReactiveMongoRepositories(basePackages = "com.github.charlesluxinger.bytebank.infra.repository")
public class MongoDBConfig extends AbstractReactiveMongoConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        Converter<Decimal128, BigDecimal> decimal128ToBigDecimal = new Converter<Decimal128, BigDecimal>() {
            @Override
            public BigDecimal convert(final Decimal128 decimal128) {
                return decimal128.bigDecimalValue();
            }
        };

        Converter<BigDecimal, Decimal128> bigDecimalToDecimal128 = new Converter<BigDecimal, Decimal128>() {
            @Override
            public Decimal128 convert(final BigDecimal bigDecimal) {
                return new Decimal128(bigDecimal);
            }
        };

        return new MongoCustomConversions(List.of(decimal128ToBigDecimal, bigDecimalToDecimal128));
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }

}
