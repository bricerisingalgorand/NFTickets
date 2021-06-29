package com.pago.core.quotes.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransactionGatewayConfig {
    @JsonProperty
    private String url;
    @JsonProperty
    private int port;
    @JsonProperty
    private String token = "";
}
