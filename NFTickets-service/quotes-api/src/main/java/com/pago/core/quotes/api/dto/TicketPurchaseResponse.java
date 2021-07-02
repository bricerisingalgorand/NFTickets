package com.pago.core.quotes.api.dto;

import lombok.Data;
import org.joda.time.DateTime;

@Data
public class TicketPurchaseResponse {

    private Long id;
    private DateTime createTime;
    private String ticketPurchaseAccount;
    private String eventId;
    private String seatNumber;
    private String zone;
    private Long price;
    private Integer assetId;
    private String eventAccount;
    private Long appId;

    public TicketPurchaseResponse() {}
}
