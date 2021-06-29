package com.pago.core.quotes.dao.service;

import com.pago.core.quotes.dao.models.EventItem;
import com.pago.core.quotes.dao.models.TicketPurchase;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.List;

public class TicketPurchaseTableService extends AbstractDAO<TicketPurchase> {
    @Inject
    public TicketPurchaseTableService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void save(TicketPurchase ticketPurchase) {
        persist(ticketPurchase);
    }

    public List<TicketPurchase> getTicketPurchaseList() {
        return list(query("from ticketPurchase"));
    }

    public TicketPurchase getTicketPurchase(Long id) {
        return get(id);
    }
}
