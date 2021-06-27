package com.pago.core.quotes.api.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class ZoneResponse {
    private Long id;
    private String name;
    private Integer seats;
    private BigInteger price;

    public ZoneResponse() {}
}
