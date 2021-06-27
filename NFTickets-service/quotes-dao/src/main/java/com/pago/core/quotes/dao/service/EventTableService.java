package com.pago.core.quotes.dao.service;

import javax.inject.Inject;
import com.pago.core.quotes.dao.models.EventItem;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class EventTableService extends AbstractDAO<EventItem> {
    @Inject
    public EventTableService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void save(EventItem event) {
        persist(event);
    }

    public List<EventItem> getEvents() {
        return list(query("from events"));
    }

    public EventItem getEvent(Long id) {
        return get(id);
    }
}
