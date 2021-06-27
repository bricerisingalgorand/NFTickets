package com.pago.core.quotes.api.dto;

import lombok.Data;

@Data
public class VenueRequest {
    private String name;
    private String description;

    public VenueRequest() {}
}
