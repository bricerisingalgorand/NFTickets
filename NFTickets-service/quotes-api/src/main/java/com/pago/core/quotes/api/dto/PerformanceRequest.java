package com.pago.core.quotes.api.dto;

import lombok.Data;

@Data
public class PerformanceRequest {
    private String name;
    private String description;

    public PerformanceRequest() {}
}
