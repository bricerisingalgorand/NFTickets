package com.pago.core.quotes.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pago.core.quotes.api.util.DozerConfiguration;
import com.pago.core.quotes.dao.config.DynamoDBConfiguration;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SeedApplicationConfiguration extends Configuration {
    @Valid
    @NotNull
    @JsonProperty("dynamodb")
    DynamoDBConfiguration dynamoDBServiceConfiguration;

    @Valid
    @JsonProperty("swagger")
    SwaggerDetailsConfiguration swaggerDetailsConfiguration;

    @Valid
    @JsonProperty("dozer")
    DozerConfiguration dozerConfiguration;

    public DynamoDBConfiguration getDynamoDB() {
        return dynamoDBServiceConfiguration;
    }

    public SwaggerDetailsConfiguration getSwaggerConfiguration() {
        if (swaggerDetailsConfiguration == null) {
            return new SwaggerDetailsConfiguration();
        }
        return swaggerDetailsConfiguration;
    }

    public DozerConfiguration getDozerConfiguration() {
        if (dozerConfiguration == null) {
            return new DozerConfiguration();
        }
        return dozerConfiguration;
    }
}
