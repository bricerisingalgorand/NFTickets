package com.pago.core.quotes.dao.service;

import com.pago.core.quotes.dao.models.EventItem;
import com.pago.core.quotes.dao.models.PerformanceItem;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.util.List;

public class PerformanceTableService extends AbstractDAO<PerformanceItem> {
    @Inject
    public PerformanceTableService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void save(PerformanceItem event) {
        persist(event);
    }

    public List<PerformanceItem> getPerformances() {
        return list(query("from performance"));
    }

    public PerformanceItem getPerformance(Long eventId) {
        return query("select P from performance P where P.eventid = " + eventId).getSingleResult();
    }
}
