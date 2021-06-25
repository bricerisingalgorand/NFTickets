package com.pago.core.quotes.api;

import com.codahale.metrics.annotation.Timed;
import com.pago.core.quotes.api.dto.Page;
import com.pago.core.quotes.api.dto.QuoteRequest;
import com.pago.core.quotes.api.dto.QuoteResponse;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/quote")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface QuoteResource {

    @POST
    @Path("/")
    public QuoteResponse save(QuoteRequest quote);

    @GET
    @Path("/")
    @Timed
    public Page<QuoteResponse> getQuotes(
        @QueryParam("createDate") String createDate,
        @QueryParam("page_number") @DefaultValue("0") Integer pageNumber,
        @QueryParam("page_size") @DefaultValue("10") Integer pageSize
    );

    @GET
    @Path("/{id}")
    @Timed
    public QuoteResponse getQuote(@NotNull @PathParam("id") String id);
}
