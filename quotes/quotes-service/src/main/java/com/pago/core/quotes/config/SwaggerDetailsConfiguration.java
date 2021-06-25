package com.pago.core.quotes.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SwaggerDetailsConfiguration {
    @JsonProperty
    private String title = "quotes";
    @JsonProperty
    private String description = "";
    @JsonProperty
    private String resourcePackage = "com.pago.core.quotes";
}
