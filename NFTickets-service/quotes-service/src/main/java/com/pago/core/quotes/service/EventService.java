package com.pago.core.quotes.service;

import com.pago.core.quotes.api.dto.Page;
import com.pago.core.quotes.dao.models.EventItem;
import com.pago.core.quotes.dao.service.EventTableService;
import com.pago.core.quotes.dao.service.PerformanceTableService;

import javax.inject.Inject;

public class EventService {

    @Inject
    private EventTableService eventTableService;
    @Inject
    private PerformanceTableService performanceTableService;

    public void save(EventItem event) {
        eventTableService.save(event);

    }

    public EventItem getEvent(Long id) {
        EventItem event = eventTableService.getEvent(id);
        return event;
    }

    public Page<EventItem> getEvents(Integer pageNumber, Integer pageSize) {
        return new Page(eventTableService.getEvents(), pageNumber, pageSize);
    }
}
