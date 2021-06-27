package com.pago.core.quotes.api.dto;

import lombok.Data;

@Data
public class PerformanceResponse {
    private Long id;
    private String name;
    private String description;

    public PerformanceResponse() {}
}
