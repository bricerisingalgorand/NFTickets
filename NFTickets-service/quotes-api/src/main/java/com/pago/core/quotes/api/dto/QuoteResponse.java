package com.pago.core.quotes.api.dto;

import lombok.Data;

@Data
public class QuoteResponse {
    private String quote;
    private String createDate;
    private Long id;

    public QuoteResponse() {}
}
