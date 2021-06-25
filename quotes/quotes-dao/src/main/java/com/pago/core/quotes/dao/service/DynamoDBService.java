package com.pago.core.quotes.dao.service;


import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.pago.core.quotes.dao.config.DynamoDBConfiguration;

public class DynamoDBService {
    DynamoDBMapper mapper;
    DynamoDBMapperConfig dynamoDBMapperConfig;

    public DynamoDBService(DynamoDBConfiguration dynamoDBConfiguration) {
        AmazonDynamoDBClientBuilder clientBuilder = AmazonDynamoDBClientBuilder.standard();
        if (dynamoDBConfiguration.getEndpoint() != null) {
            clientBuilder.setEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                    dynamoDBConfiguration.getEndpoint(),
                    dynamoDBConfiguration.getRegion()
            ));
        } else {
            clientBuilder.withRegion(dynamoDBConfiguration.getRegion());
        }
        AmazonDynamoDB client = clientBuilder.build();
        this.dynamoDBMapperConfig = new DynamoDBMapperConfig.Builder()
                .withTableNameOverride(
                        DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement(dynamoDBConfiguration.getTable())
                ).build();
        this.mapper = new DynamoDBMapper(client, this.dynamoDBMapperConfig);
    }

    public void save(Object obj) {
        this.mapper.save(obj);
    }

    public <T> PaginatedQueryList<T> query(Class<T> clazz, DynamoDBQueryExpression<T> queryExpression) {
        return this.mapper.query(clazz, queryExpression);
    }

    public <T> PaginatedScanList<T> scan(Class<T> clazz, DynamoDBScanExpression scanExpression) {
        return this.mapper.scan(clazz, scanExpression);
    }
    public <T> T get(Class<T> clazz, Object hashKey) {
        return this.mapper.load(clazz, hashKey);
    }
}

