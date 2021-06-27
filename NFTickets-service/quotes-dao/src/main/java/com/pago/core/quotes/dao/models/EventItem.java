package com.pago.core.quotes.dao.models;

import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity(name = "events")
public class EventItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(name = "createTime")
    private DateTime createTime;
    @NotNull
    @Column(name = "startTime")
    private DateTime startTime;
    @NotNull
    @Column(name = "endTime")
    private DateTime endTime;

    @Transient
    private PerformanceItem performance;
    @Transient
    private VenueItem venue;
    @Transient
    private List<ZoneItem> zones;

    public EventItem() {}
}
