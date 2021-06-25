package com.pago.core.quotes.service;

import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import com.pago.core.quotes.api.dto.Page;
import com.pago.core.quotes.api.util.DozerMapperService;
import com.pago.core.quotes.dao.models.QuoteItem;
import com.pago.core.quotes.dao.service.QuoteTableService;

import javax.inject.Inject;
import java.util.List;

public class QuoteService {

    @Inject
    QuoteTableService quoteTableService;
    @Inject
    DozerMapperService mapper;

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
