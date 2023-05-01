package it.loooop.service;

import it.loooop.client.DynamoDB;
import it.loooop.model.App;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class AppService {
    DynamoDB dynamoDb = new DynamoDB();
    String appsTable = System.getenv("DYNAMODB_APPS_TABLE");

    DynamoDbTable<App> mappedTable = dynamoDb.client()
            .table(appsTable, TableSchema.fromBean(App.class));

    public App getApp(String appId){
        return mappedTable.getItem(Key.builder().partitionValue(appId).build());
    }

}
