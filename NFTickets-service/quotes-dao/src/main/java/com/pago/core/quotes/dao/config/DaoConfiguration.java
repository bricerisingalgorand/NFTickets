package com.pago.core.quotes.dao.config;

import io.dropwizard.db.DataSourceFactory;

public interface DaoConfiguration {
    public DataSourceFactory getDataSourceFactory();
}
