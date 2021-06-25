package com.pago.core.quotes.dao.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DynamoDBConfiguration {
    @JsonProperty
    private String table;
    @JsonProperty
    private String region;
    @JsonProperty
    private String endpoint;
}
