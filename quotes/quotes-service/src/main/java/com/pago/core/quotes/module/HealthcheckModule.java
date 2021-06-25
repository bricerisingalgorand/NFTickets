package com.pago.core.quotes.module;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.setup.Environment;

public class HealthcheckModule extends AbstractModule {
    @Provides
    public HealthCheckRegistry registry(Environment environment) {
        return environment.healthChecks();
    }

    @Override
    protected void configure() { }
}
