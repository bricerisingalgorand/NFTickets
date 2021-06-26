package com.pago.core.quotes.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pago.core.quotes.api.util.DozerConfiguration;
import com.pago.core.quotes.dao.config.DaoConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SeedApplicationConfiguration extends Configuration implements DaoConfiguration {
    @Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    @Valid
    @JsonProperty("swagger")
    SwaggerDetailsConfiguration swaggerDetailsConfiguration;

    @Valid
    @JsonProperty("dozer")
    DozerConfiguration dozerConfiguration;

    public DataSourceFactory getDataSourceFactory() {
        return database;
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
