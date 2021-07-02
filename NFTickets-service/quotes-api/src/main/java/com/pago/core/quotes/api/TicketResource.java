package com.pago.core.quotes.api;

import com.codahale.metrics.annotation.Timed;
import com.pago.core.quotes.api.dto.EventRequest;
import com.pago.core.quotes.api.dto.EventResponse;
import com.pago.core.quotes.api.dto.Page;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/ticket")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface TicketResource {

    @POST
    @Path("/")
    public EventResponse save(EventRequest quote);

    @GET
    @Path("/")
    @Timed
    public Page<EventResponse> getEvents(
            @QueryParam("page_number") @DefaultValue("0") Integer pageNumber,
            @QueryParam("page_size") @DefaultValue("10") Integer pageSize
    );

    @GET
    @Path("/{id}")
    @Timed
    public EventResponse getEvent(@NotNull @PathParam("id") Long id);
}

