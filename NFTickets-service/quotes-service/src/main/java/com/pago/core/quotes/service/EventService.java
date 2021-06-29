package com.pago.core.quotes.service;

import com.pago.core.quotes.api.EventResourceException;
import com.pago.core.quotes.api.dto.Page;
import com.pago.core.quotes.api.dto.TicketPurchaseResponse;
import com.pago.core.quotes.dao.models.EventItem;
import com.pago.core.quotes.dao.models.TicketPurchase;
import com.pago.core.quotes.dao.service.EventTableService;
import com.pago.core.quotes.dao.service.PerformanceTableService;
import com.pago.core.quotes.dao.service.TicketPurchaseTableService;

import javax.inject.Inject;

public class EventService {

    @Inject
    private EventTableService eventTableService;
    @Inject
    private PerformanceTableService performanceTableService;
    @Inject
    private TicketPurchaseTableService ticketPurchaseTableService;

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

    public void save(TicketPurchase ticketPurchase)   {
        ticketPurchaseTableService.save(ticketPurchase);
    }

    public Page<TicketPurchaseResponse> getTicketPurchaseList(String ticketPurchaseAccount, Integer pageNumber, Integer pageSize) {
        return new Page(ticketPurchaseTableService.getTicketPurchaseList(), pageNumber, pageSize);
    }

    public TicketPurchase getTicketPurchase(Long ticketPurchaseId ) {
        return ticketPurchaseTableService.getTicketPurchase(ticketPurchaseId);
    }
}
