package com.pago.core.quotes.dao.config;

import com.pago.core.quotes.dao.models.QuoteItem;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;

public class DaoBundle extends HibernateBundle<DaoConfiguration> {

    public DaoBundle() {
        super(QuoteItem.class);
    }

    @Override
    public PooledDataSourceFactory getDataSourceFactory(DaoConfiguration configuration) {
        return configuration.getDataSourceFactory();
    }
}
