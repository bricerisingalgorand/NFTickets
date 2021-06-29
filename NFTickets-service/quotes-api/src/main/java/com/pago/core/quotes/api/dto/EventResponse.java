package com.pago.core.quotes.api.dto;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;

@Data
public class EventResponse {

    private Long id;
    private DateTime createTime;
    private DateTime startTime;
    private DateTime endTime;
    private PerformanceResponse performance;
    private VenueResponse venue;
    private List<ZoneResponse> zones;
    private String creator;

    public EventResponse() {}
}
