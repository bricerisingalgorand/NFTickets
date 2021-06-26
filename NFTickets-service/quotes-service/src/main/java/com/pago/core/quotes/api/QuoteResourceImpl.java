package com.pago.core.quotes.api;

import com.codahale.metrics.annotation.Timed;
import com.pago.core.quotes.api.dto.Page;
import com.pago.core.quotes.api.dto.QuoteRequest;
import com.pago.core.quotes.api.dto.QuoteResponse;
import com.pago.core.quotes.api.util.DozerMapperService;
import com.pago.core.quotes.dao.models.QuoteItem;
import com.pago.core.quotes.service.QuoteService;
import io.dropwizard.hibernate.UnitOfWork;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

public class QuoteResourceImpl implements QuoteResource {

    @Inject
    private QuoteService quoteService;
    @Inject
    private DozerMapperService mapper;

    @Override
    @Timed
    @UnitOfWork
    public QuoteResponse save(QuoteRequest quoteRequest) {
        QuoteItem quote = mapper.map(quoteRequest, QuoteItem.class);
        quoteService.save(quote);
        return mapper.map(quote, QuoteResponse.class);
    }

    @Override
    @Timed
    @UnitOfWork
    public Page<QuoteResponse> getQuotes(Integer pageNumber, Integer pageSize) {
        Page<QuoteItem> quotes = quoteService.getQuotes(pageNumber, pageSize);
        return quotes.map(mapper, QuoteResponse.class);
    }

    @Override
    @Timed
    @UnitOfWork
    public QuoteResponse getQuote(@NotNull Long id) {
        QuoteItem quote = quoteService.getQuote(id);
        return mapper.map(quote, QuoteResponse.class);
    }
}
