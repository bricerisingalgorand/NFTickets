package com.pago.core.quotes.api.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode()
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionCallbackResponse {
    @JsonProperty("transactionEnvelopeId")
    private String transactionEnvelopeId;
    @JsonProperty("status")
    private long status;
    @JsonProperty("result")
    private String result;
}
