package com.pago.core.quotes.dao.util;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.Instant;

public class DynamoDBLocalDateTimeConverter implements DynamoDBTypeConverter<String, Instant> {
    @Override
    public String convert( final Instant time ) {

        return time.toString();
    }

    @Override
    public Instant unconvert( final String stringValue ) {

        return Instant.parse(stringValue);
    }
}
