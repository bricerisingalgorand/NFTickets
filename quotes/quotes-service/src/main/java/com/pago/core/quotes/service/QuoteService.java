package com.pago.core.quotes.service;

import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import com.pago.core.quotes.api.dto.Page;
import com.pago.core.quotes.api.util.DozerMapperService;
import com.pago.core.quotes.dao.models.QuoteItem;
import com.pago.core.quotes.dao.service.QuoteTableService;

import javax.inject.Inject;

public class QuoteService {

    @Inject
    QuoteTableService quoteTableService;
    @Inject
    DozerMapperService mapper;

    public void save(QuoteItem quote) {
        quoteTableService.saveQuote(quote);
    }

    public QuoteItem getQuote(String id) {
        return quoteTableService.getQuote(id);
    }

    public Page<QuoteItem> getQuotes(String createDate, Integer pageNumber, Integer pageSize) {
        if (createDate == null) {
            return getAllQuotes(pageNumber, pageSize);
        }
        return getQuotesForDate(createDate, pageNumber, pageSize);
    }

    private Page<QuoteItem> getAllQuotes(Integer pageNumber, Integer pageSize) {
        PaginatedList<QuoteItem> quoteItems = quoteTableService.getQuotes();
        return new Page<QuoteItem>(quoteItems, pageNumber, pageSize);
    }

    private Page<QuoteItem> getQuotesForDate(String createDate, Integer pageNumber, Integer pageSize) {
        PaginatedList<QuoteItem> quoteItems = quoteTableService.getQuotes(createDate);
        return new Page<QuoteItem>(quoteItems, pageNumber, pageSize);
    }
}
