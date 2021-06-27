package com.pago.core.quotes.api.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class ZoneRequest {
    private String name;
    private Integer seats;
    private BigInteger price;

    public ZoneRequest() {}
}
