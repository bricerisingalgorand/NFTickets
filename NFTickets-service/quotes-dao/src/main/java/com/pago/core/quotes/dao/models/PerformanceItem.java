package com.pago.core.quotes.dao.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Embeddable
public class PerformanceItem {
    @NotNull
    @Column(name = "name", table = "performance")
    private String name;
    @NotNull
    @Column(name = "description", table = "performance")
    private String description;

    public PerformanceItem() {}
}
