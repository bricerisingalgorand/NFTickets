package com.pago.core.quotes.dao.config;

import com.pago.core.quotes.dao.models.*;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;

public class DaoBundle extends HibernateBundle<DaoConfiguration> {

    public DaoBundle() {
        super(
                QuoteItem.class,
                EventItem.class,
                PerformanceItem.class,
                VenueItem.class,
                ZoneItem.class
        );
    }

    @Override
    public PooledDataSourceFactory getDataSourceFactory(DaoConfiguration configuration) {
        return configuration.getDataSourceFactory();
    }
}
