package com.pago.core.quotes.api.dto;

import lombok.Data;

@Data
public class QuoteResponse {
    private String quote;
    private Long id;

    public QuoteResponse() {}
}
