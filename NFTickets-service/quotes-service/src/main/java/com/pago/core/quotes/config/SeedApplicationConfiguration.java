package com.pago.core.quotes.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pago.core.quotes.api.util.DozerConfiguration;
import com.pago.core.quotes.dao.config.PostgresConfiguration;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SeedApplicationConfiguration extends Configuration {
    @Valid
    @NotNull
    @JsonProperty("postgres")
    PostgresConfiguration postgresConfiguration;

    @Valid
    @JsonProperty("swagger")
    SwaggerDetailsConfiguration swaggerDetailsConfiguration;

    @Valid
    @JsonProperty("dozer")
    DozerConfiguration dozerConfiguration;

    public PostgresConfiguration getPostgresConfiguration() {
        return postgresConfiguration;
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
