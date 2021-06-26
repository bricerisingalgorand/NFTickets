package com.pago.core.quotes.dao.config;

import com.google.inject.AbstractModule;
import org.hibernate.SessionFactory;

public class DaoModule extends AbstractModule {

    private final DaoBundle daoBundle;

    public DaoModule(DaoBundle daoBundle) {
        this.daoBundle = daoBundle;
    }

    @Override
    protected void configure() {
        bind(SessionFactory.class).toInstance(daoBundle.getSessionFactory());
    }
}
