package com.pago.core.quotes.dao.models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransactionCallback {
    private String transactionEnvelopeId;
    private long status;
    private String result;
}
