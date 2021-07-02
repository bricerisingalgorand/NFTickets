package com.pago.core.quotes.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.pago.core.quotes.config.SeedApplicationConfiguration;
import com.pago.core.quotes.config.TransactionGatewayConfig;
import com.pago.core.quotes.config.AlgodConfiguration;
import com.pago.core.transactiongateway.client.TransactionGatewayClient;

public class TransactionGatewayModule extends AbstractModule {

    @Provides
    public TransactionGatewayClient transactionGatewayClient(SeedApplicationConfiguration configuration) {
        TransactionGatewayConfig txgwConfig = configuration.getTransactionGatewayConfig();
        return new TransactionGatewayClient(
                txgwConfig.getProtocol(),
                txgwConfig.getUrl(),
                txgwConfig.getPort(),
                txgwConfig.getToken()
        );
    }

    @Provides
    public AlgodConfiguration algodConfiguration(SeedApplicationConfiguration configuration) {
        return new AlgodConfiguration();
    }
}
