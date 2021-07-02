package com.pago.core.quotes.dao.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Embeddable
public class VenueItem {
    @NotNull
    @Column(name = "name", table = "venue")
    private String name;
    @NotNull
    @Column(name = "description", table = "venue")
    private String description;
    @NotNull
    @Column(name = "totalSeats", table = "venue")
    private int totalSeats;

    public VenueItem() {}
}
