package com.pago.core.quotes.api.dto;

import lombok.Data;

@Data
public class VenueResponse {
    private Long id;
    private String name;
    private String description;

    public VenueResponse() {}
}
