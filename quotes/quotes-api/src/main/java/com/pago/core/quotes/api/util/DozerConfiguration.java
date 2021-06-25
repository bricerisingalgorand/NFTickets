package com.pago.core.quotes.api.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DozerConfiguration {
    @JsonProperty
    private List<String> mappingFiles = new ArrayList();
}
