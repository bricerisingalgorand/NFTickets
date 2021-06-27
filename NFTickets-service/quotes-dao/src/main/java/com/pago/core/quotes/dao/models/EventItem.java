package com.pago.core.quotes.dao.models;

import lombok.Data;
import org.hibernate.annotations.Formula;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity(name = "events")
@SecondaryTable(name = "performance", pkJoinColumns = @PrimaryKeyJoinColumn(name = "eventid"))
@SecondaryTable(name = "venue", pkJoinColumns = @PrimaryKeyJoinColumn(name = "eventid"))
@SecondaryTable(name = "zones")
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

    @Embedded
    private PerformanceItem performance;
    @Embedded
    private VenueItem venue;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "zones", joinColumns = @JoinColumn(name = "eventid"))
    private List<ZoneItem> zones;

    public EventItem() {}
}
