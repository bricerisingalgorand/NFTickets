package com.pago.core.quotes.api.util;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class DozerModule extends AbstractModule {
    @Provides
    private DozerMapperService getDozerBeanMapper(DozerConfiguration dozerConfiguration) {
        return new DozerMapperService(dozerConfiguration);
    }
}
