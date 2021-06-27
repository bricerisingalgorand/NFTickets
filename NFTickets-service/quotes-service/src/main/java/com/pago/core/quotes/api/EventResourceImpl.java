package com.pago.core.quotes.api;

import com.pago.core.quotes.api.dto.*;
import com.pago.core.quotes.api.util.DozerMapperService;
import com.pago.core.quotes.dao.models.EventItem;
import com.pago.core.quotes.service.EventService;
import io.dropwizard.hibernate.UnitOfWork;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

public class EventResourceImpl implements EventResource {

    @Inject
    private EventService eventService;
    @Inject
    private DozerMapperService mapper;

    @Override
    @UnitOfWork
    public EventResponse save(EventRequest quote) {
        EventItem event = mapper.map(quote, EventItem.class);
        eventService.save(event);
        return mapper.map(event, EventResponse.class);
    }

    @Override
    @UnitOfWork
    public Page<EventResponse> getEvents(Integer pageNumber, Integer pageSize) {
        Page<EventItem> events = eventService.getEvents(pageNumber, pageSize);
        return events.map(mapper, EventResponse.class);
    }

    @Override
    @UnitOfWork
    public EventResponse getEvent(@NotNull Long id) {
        EventItem event = eventService.getEvent(id);
        return mapper.map(event, EventResponse.class);
    }
}
