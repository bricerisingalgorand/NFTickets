package com.pago.core.quotes.dao.service;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.pago.core.quotes.dao.models.QuoteItem;

import javax.inject.Inject;

public class QuoteTableService {
    @Inject
    DynamoDBService dynamoDBService;

    public void saveQuote(QuoteItem quote) {
        dynamoDBService.save(quote);
    }

    public PaginatedList<QuoteItem> getQuotes() {
        return dynamoDBService.scan(QuoteItem.class, new DynamoDBScanExpression());
    }

    public PaginatedList<QuoteItem> getQuotes(String createDate) {
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression();
        queryExpression
                .withIndexName("CreateDate")
                .withKeyConditionExpression("createDate = :createDate")
                .addExpressionAttributeValuesEntry(":createDate", new AttributeValue().withS(createDate))
                .withConsistentRead(false);
        return dynamoDBService.query(QuoteItem.class, queryExpression);
    }

    public QuoteItem getQuote(String id) {
        return dynamoDBService.get(QuoteItem.class, id);
    }
}
