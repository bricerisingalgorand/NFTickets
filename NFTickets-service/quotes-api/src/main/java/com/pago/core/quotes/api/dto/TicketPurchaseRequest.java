package com.pago.core.quotes.api.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TicketPurchaseRequest {

    private String ticketPurchaseAccount;
    private String eventId;
    private String seatNumber;
    private String zone;
    private Long price;
    private Integer assetId;
    private String eventAccount;
    private Long appId;

    public TicketPurchaseRequest() {}

}
