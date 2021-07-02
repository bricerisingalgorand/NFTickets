package com.pago.core.quotes.api;

import com.pago.core.quotes.api.dto.*;
import com.pago.core.quotes.api.util.DozerMapperService;
import com.pago.core.quotes.dao.models.EventItem;
import com.pago.core.quotes.dao.models.TicketPurchase;
import com.pago.core.quotes.dao.models.TransactionCallback;
import com.pago.core.quotes.service.EventService;
import com.pago.core.quotes.smartcontract.EventContractFactory;
import com.pago.core.quotes.smartcontract.TicketPurchaseFactory;
import com.pago.core.transactiongateway.api.transaction.dto.TransactionCallbackRequest;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

public class EventResourceImpl implements EventResource {

    private final Logger log = LoggerFactory.getLogger("EventResourceImpl");

    @Inject
    private EventService eventService;
    @Inject
    private DozerMapperService mapper;
    @Inject
    private EventContractFactory eventContractFactory;
    @Inject
    private TicketPurchaseFactory ticketPurchaseFactory;

    @Override
    @UnitOfWork
    public EventResponse save(EventRequest quote)  {
        EventItem event = mapper.map(quote, EventItem.class);
        eventService.save(event);
        try {
            eventContractFactory.createEventContract(event);
        } catch (EventResourceException e) {
            e.printStackTrace();
            log.error("error creating event contract: ", e);
        }
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

    @Override
    @UnitOfWork
    public TicketPurchaseResponse save(TicketPurchaseRequest ticketPurchaseRequest) {
        TicketPurchase ticketPurchase = mapper.map(ticketPurchaseRequest, TicketPurchase.class);
        eventService.save(ticketPurchase);
        try {
            ticketPurchase = ticketPurchaseFactory.createTicketPurchase(ticketPurchase);
        } catch (EventResourceException e) {
            e.printStackTrace();
            log.error("error processing ticket purchase: ", e);
        }
        return mapper.map(ticketPurchase, TicketPurchaseResponse.class);
    }

    @Override
    @UnitOfWork
    public Page<TicketPurchaseResponse> getTicketPurchaseList(String ticketPurchaseAccount, Integer pageNumber, Integer pageSize) {
        Page<TicketPurchaseResponse> ticketPurchaseResponsePage = eventService.getTicketPurchaseList(ticketPurchaseAccount, pageNumber, pageSize);
        return ticketPurchaseResponsePage.map(mapper, TicketPurchaseResponse.class);
    }

    @Override
    @UnitOfWork
    public TicketPurchaseResponse getTicketPurchase(@NotNull Long id) {
        TicketPurchase ticketPurchase = eventService.getTicketPurchase(id);
        return mapper.map(ticketPurchase, TicketPurchaseResponse.class);
    }

    @Override
    public TransactionCallbackResponse transactionCallback(TransactionCallbackRequest transactionCallbackRequest) {
        TransactionCallback transactionCallback = mapper.map(transactionCallbackRequest, TransactionCallback.class);
        TransactionCallback transactionCallback1 = eventService.transactionCallback(transactionCallback);
        return mapper.map(transactionCallback1, TransactionCallbackResponse.class);
    }

}
