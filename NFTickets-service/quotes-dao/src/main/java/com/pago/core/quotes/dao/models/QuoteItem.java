package com.pago.core.quotes.dao.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Table(name = "quotes")
@Entity
public class QuoteItem {

    public QuoteItem() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(name = "quote")
    private String quote;
}
