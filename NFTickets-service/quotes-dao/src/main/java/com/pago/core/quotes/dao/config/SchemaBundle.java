package com.pago.core.quotes.dao.config;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;

public class SchemaBundle<T extends Configuration & DaoConfiguration>
        extends MigrationsBundle<T> {

        @Override
        public DataSourceFactory getDataSourceFactory(T configuration) {
            return configuration.getDataSourceFactory();
        }
}
