package com.pago.core.quotes.api;

import com.codahale.metrics.annotation.Timed;
import com.pago.core.quotes.api.dto.*;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/event")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface EventResource {

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
