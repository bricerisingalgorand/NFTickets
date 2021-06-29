package com.pago.core.quotes.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import javax.validation.constraints.NotNull;

public class AlgodConfiguration {

    @NotNull(message = "algod protocol")
    @JsonProperty
    public String algodProtocol;
    @NotNull(message = "algod api host")
    @JsonProperty
    public String algodNetworkHost;
    @NotNull(message = "algod api port")
    @JsonProperty
    public int algodNetworkPort;
    @NotNull(message = "algod api token")
    @JsonProperty
    public String algodApiToken;
    @NotNull(message = "algod apiTokenAuthHeader")
    @JsonProperty
    public String algodApiTokenAuthHeader;
}
