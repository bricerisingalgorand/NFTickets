package com.pago.core.quotes.dao.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
@Embeddable
public class ZoneItem {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;
    @NotNull
    @Column(name = "name", table = "zones")
    private String name;
    @NotNull
    @Column(name = "seats", table = "zones")
    private Integer seats;
    @NotNull
    @Column(name = "price", table = "zones")
    private BigInteger price;

    public ZoneItem() {}
}
