package com.pago.core.quotes.api.dto;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;

@Data
public class EventRequest {

    private DateTime startTime;
    private DateTime endTime;
    private PerformanceRequest performance;
    private VenueRequest venue;
    private List<ZoneRequest> zones;

    public EventRequest() {}
}
