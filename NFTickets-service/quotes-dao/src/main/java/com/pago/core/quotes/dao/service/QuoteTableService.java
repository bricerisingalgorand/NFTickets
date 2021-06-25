package com.pago.core.quotes.dao.service;

import com.pago.core.quotes.dao.models.QuoteItem;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.List;

public class QuoteTableService extends AbstractDAO<QuoteItem> {

    @Inject
    public QuoteTableService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void saveQuote(QuoteItem quote) {
        persist(quote);
    }

    public List<QuoteItem> getQuotes() {
        return list(query("select *"));
    }

    public QuoteItem getQuote(Long id) {
        return get(id);
    }
}
