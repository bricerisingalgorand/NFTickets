package com.pago.core.quotes.dao.models;

import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "tickets")
public class TicketPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(name = "createTime")
    private DateTime createTime;
    @NotNull
    @Column(name = "ticketPurchaseAccount")
    private String ticketPurchaseAccount;
    @NotNull
    @Column(name = "eventId")
    private String eventId;
    @NotNull
    @Column(name = "seatNumber")
    private String seatNumber;
    @NotNull
    @Column(name = "zone")
    private String zone;
    @NotNull
    @Column(name = "price")
    private Long price;
    @NotNull
    @Column(name = "assetId")
    private Integer assetId;
    @NotNull
    @Column(name = "eventAccount")
    private String eventAccount;
    @NotNull
    @Column(name = "appId")
    private Long appId;


    public TicketPurchase() {}

    @PrePersist
    protected void onCreate() {
        createTime = DateTime.now();
    }
}
