package com.pago.core.quotes.dao.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
@Entity(name = "zone")
public class ZoneItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(name = "eventId")
    private Long eventId;
    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "seats")
    private Integer seats;
    @NotNull
    @Column(name = "price")
    private BigInteger price;

    public ZoneItem() {}
}
