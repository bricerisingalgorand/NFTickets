package com.pago.core.quotes.service;

import com.pago.core.quotes.api.EventResourceException;
import com.pago.core.quotes.api.dto.Page;
import com.pago.core.quotes.api.dto.TicketPurchaseResponse;
import com.pago.core.quotes.dao.models.EventItem;
import com.pago.core.quotes.dao.models.TicketPurchase;
import com.pago.core.quotes.dao.models.TransactionCallback;
import com.pago.core.quotes.dao.service.EventTableService;
import com.pago.core.quotes.dao.service.PerformanceTableService;
import com.pago.core.quotes.dao.service.TicketPurchaseTableService;
import com.pago.core.quotes.smartcontract.EventContractFactory;
import com.pago.core.transactiongateway.api.transaction.dto.AppCreateResponse;
import com.pago.core.transactiongateway.api.transaction.dto.TransactionEnvelopeResponse;
import com.pago.core.transactiongateway.client.TransactionGatewayClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class EventService {

    private final Logger log = LoggerFactory.getLogger("EventService");

    @Inject
    private EventTableService eventTableService;
    @Inject
    private PerformanceTableService performanceTableService;
    @Inject
    private TicketPurchaseTableService ticketPurchaseTableService;
    @Inject
    private TransactionGatewayClient transactionGatewayClient;

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

    public TransactionCallback transactionCallback(TransactionCallback transactionCallback) {
        log.info("processing transaction callback " + transactionCallback);
        String transactionEnvelopeId = transactionCallback.getTransactionEnvelopeId();
        TransactionEnvelopeResponse transactionEnvelopeResponse = transactionGatewayClient.getTransactionEnvelope(transactionEnvelopeId);
        if (transactionEnvelopeResponse != null) {
            String[] args = transactionEnvelopeResponse.getName().split(":");
            String operation = args[0];
            String eventId = args[1];
            switch (operation) {
                case "CREATE":
                    log.info("processing CREATE callback for event ID: " + eventId);
                    EventItem event = getEvent(Long.valueOf(eventId));
                    event.setAppId(((AppCreateResponse)transactionEnvelopeResponse.getTransactionInterfaceResponse()).getApplicationId());
                    eventTableService.save(event);
                    break;
                default:
                    log.error("unsupported callback operation: " + transactionEnvelopeResponse.getName());
            }
        }
        return transactionCallback;
    }
}
