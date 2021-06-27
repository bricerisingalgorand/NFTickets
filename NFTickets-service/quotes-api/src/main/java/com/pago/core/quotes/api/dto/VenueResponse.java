package com.pago.core.quotes.api.dto;

import lombok.Data;

@Data
public class VenueResponse {
    private String name;
    private String description;

    public VenueResponse() {}
}
