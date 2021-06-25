package com.pago.core.quotes.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.pago.core.quotes.config.SeedApplicationConfiguration;
import com.pago.core.quotes.dao.service.DynamoDBService;

public class DynamoDBModule extends AbstractModule {

    public DynamoDBModule() {}

    @Override
    protected void configure() { }

    @Provides
    public DynamoDBService getDynamoDbService(SeedApplicationConfiguration configuration) {
        return new DynamoDBService(configuration.getDynamoDB());
    }
}
