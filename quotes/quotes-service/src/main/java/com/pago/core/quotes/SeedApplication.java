package com.pago.core.quotes;

import com.pago.core.quotes.api.util.DozerModule;
import com.pago.core.quotes.config.*;
import com.pago.core.quotes.module.HealthcheckModule;
import com.pago.core.quotes.module.DynamoDBModule;
import com.pago.core.quotes.service.ApplicationHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import ru.vyarus.dropwizard.guice.GuiceBundle;

import java.security.Security;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SeedApplication extends Application<SeedApplicationConfiguration> {

    public static void main(final String[] args) throws Exception {
        new SeedApplication().run(args);
    }

    @Override
    public String getName() {
        return "quotes";
    }

    @Override
    public void initialize(final Bootstrap<SeedApplicationConfiguration> bootstrap) {
        bootstrap.addBundle(GuiceBundle.builder()
                .enableAutoConfig("com.pago.core.quotes")
                .modules(
                        new HealthcheckModule(),
                        new DynamoDBModule(),
                        new DozerModule()
                ).build()
        );
    }

    @Override
    public void run(final SeedApplicationConfiguration configuration,
                    final Environment environment) {
        environment.healthChecks().register("quotes", new ApplicationHealthCheck());
        setupSwagger(environment, configuration.getSwaggerConfiguration());
    }

    private void setupSwagger(final Environment environment, SwaggerDetailsConfiguration swaggerDetailsConfiguration) {
        OpenAPI oas = new OpenAPI();
        Info info = new Info()
                .version(getClass().getPackage().getImplementationVersion())
                .title(swaggerDetailsConfiguration.getTitle())
                .description(swaggerDetailsConfiguration.getDescription());

        oas.info(info);
        SwaggerConfiguration oasConfig = new SwaggerConfiguration()
                .openAPI(oas)
                .prettyPrint(true)
                .resourcePackages(Stream.of(swaggerDetailsConfiguration.getResourcePackage())
                        .collect(Collectors.toSet()));
        environment.jersey().register(new OpenApiResource()
                .openApiConfiguration(oasConfig));
    }
}
