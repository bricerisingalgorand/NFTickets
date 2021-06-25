package com.pago.core.quotes.dao.util;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGenerateStrategy;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGenerator;

import java.time.Instant;
import java.time.LocalDateTime;

public class DynamoDBLocalDateTimeGenerator implements DynamoDBAutoGenerator<String> {
    @Override
    public DynamoDBAutoGenerateStrategy getGenerateStrategy() {
        return DynamoDBAutoGenerateStrategy.CREATE;
    }

    @Override
    public String generate(String localDateTime) {
        if (localDateTime == null) {
            return LocalDateTime.now().toLocalDate().toString();
        }
        return localDateTime;
    }
}
