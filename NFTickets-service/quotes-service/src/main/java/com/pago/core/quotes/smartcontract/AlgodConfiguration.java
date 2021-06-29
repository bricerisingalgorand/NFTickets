package com.pago.core.quotes.smartcontract;

import io.dropwizard.Configuration;
import javax.validation.constraints.NotNull;

public class AlgodConfiguration extends Configuration {

    @NotNull(message = "algod protocol")
    public String algodProtocol;
    @NotNull(message = "algod api host")
    public String algodNetworkHost;
    @NotNull(message = "algod api port")
    public int algodNetworkPort;
    @NotNull(message = "algod api token")
    public String algodApiToken;
    @NotNull(message = "algod apiTokenAuthHeader")
    public String algodApiTokenAuthHeader;
}
