package com.pago.core.quotes.service;

import com.pago.core.quotes.api.dto.Page;
import com.pago.core.quotes.dao.models.QuoteItem;
import com.pago.core.quotes.dao.service.QuoteTableService;

import javax.inject.Inject;

public class QuoteService {

    @Inject
    QuoteTableService quoteTableService;

    public void save(QuoteItem quote) {
        quoteTableService.saveQuote(quote);
    }

    public QuoteItem getQuote(Long id) {
        return quoteTableService.getQuote(id);
    }

    public Page<QuoteItem> getQuotes(Integer pageNumber, Integer pageSize) {
        return new Page(quoteTableService.getQuotes(), pageNumber, pageSize);
    }
}
