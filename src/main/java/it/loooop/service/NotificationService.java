package it.loooop.service;

import it.loooop.client.DynamoDB;
import it.loooop.model.Notification;
import it.loooop.utils.Utils;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationService {

    DynamoDB dynamoDb = new DynamoDB();
    String notificationsTable = System.getenv("DYNAMODB_NOTIFICATIONS_TABLE");

    DynamoDbTable<Notification> mappedTable = dynamoDb.client()
            .table(notificationsTable, TableSchema.fromBean(Notification.class));

    DynamoDbClient dynamoDbBaseClient = dynamoDb.baseClient();

    public String persistNotification(Notification notification) {
        mappedTable.putItem(notification);
        return notification.getNotificationId();
    }

    public Pair<List<Notification>, Integer> listNotifications(String notificationId, String from, String to) {

        List<Notification> notificationList = new ArrayList<>();
        Integer unread = 0;

        //fallback to base client

        HashMap<String, AttributeValue> attributeValues = new HashMap<>();
        attributeValues.put(":partitionKey", AttributeValue.builder()
                .s(notificationId)
                .build());
        attributeValues.put(":startDate", AttributeValue.builder()
                .s(from)
                .build());
        attributeValues.put(":endDate", AttributeValue.builder()
                .s(to)
                .build());


        QueryRequest queryRequest = QueryRequest.builder().tableName(notificationsTable)
                .keyConditionExpression(
                        "notificationId = :partitionKey AND insertTimestamp BETWEEN :startDate AND :endDate")
                .expressionAttributeValues(attributeValues).build();

        QueryResponse queryResponse = dynamoDbBaseClient.query(queryRequest);

        if (queryResponse != null) {
            for (Map<String, AttributeValue> item : queryResponse.items()) {
                Notification notification = new Notification();

                //notification.setNotificationId(item.get("notificationId").s()+"#"+item.get("insertTimestamp").s());
                notification.setNotificationId(item.get("notificationId").s());
                notification.setMessage(item.get("message").s());
                notification.setInsertTimestamp(item.get("insertTimestamp").s());
                notification.setDevice(item.get("device").s());
                notification.setMessageId(item.get("messageId").s());

                if (item.containsKey("readTimestamp"))
                    notification.setReadTimestamp(item.get("readTimestamp").s());
                else
                    unread++;

                notificationList.add(notification);
            }
        }

        return Pair.of(notificationList, unread);
    }

    public void markNotificationAsRead(String notificationId, String insertTimestamp) {
        //Seems update an item isn't a use case for the enhanced client

        Map<String, AttributeValue> key = new HashMap<>();
        key.put("notificationId", AttributeValue.builder().s(notificationId).build());
        key.put("insertTimestamp", AttributeValue.builder().s(insertTimestamp).build());

        // Create an update expression to add the new field to the record
        String updateExpression = "SET readTimestamp = :readTimestamp";

        // Create a map of the attribute values for the update expression
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":readTimestamp", AttributeValue.builder().s(Utils.currentStringDate()).build());


        // Create an UpdateItemRequest with the key, update expression, and attribute
        // values
        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName(notificationsTable)
                .key(key)
                .updateExpression(updateExpression)
                .expressionAttributeValues(expressionAttributeValues)
                .build();

        // Call the DynamoDB client's updateItem method to update the record
        dynamoDbBaseClient.updateItem(request);

    }

}


//various history

//semms enahnced client is not so ehnanced...
//Adding partitionKey
        /*
        QueryConditional primaryKeyConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(notificationId)
                        .build());

        QueryConditional sortKeyConditional = QueryConditional.sortBetween(Key.builder().sortValue(from)
                .build(),Key.builder().sortValue(to)
                .build());


        //Now the expression
        Map<String, AttributeValue> expressionValues = new HashMap<>();
        expressionValues.put(":from", AttributeValue.builder().s(from).build());
        expressionValues.put(":to", AttributeValue.builder().s(to).build());

        Expression expression = Expression.builder()
                .expression("insertTimestamp BETWEEN :from AND :to")
                .expressionValues(expressionValues)
                .build();

         */

//Query mappedTable using partition key equals to notificationId and sort key between from and to
/*
        logger.info("notificationId -> {}",notificationId); //this is valorized

        QueryConditional primaryKeyConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(notificationId)
                        .build(
                        ));

        QueryConditional sortKeyConditional = QueryConditional.sortBetween(Key.builder().sortValue(from)
                .build(),Key.builder().sortValue(to)
                .build());


        for (Notification notification : mappedTable.query(
                QueryEnhancedRequest.builder().queryConditional(primaryKeyConditional).queryConditional(sortKeyConditional).build()

        ).items()) {
            notificationList.add(notification);
            if(notification.getReadTimestamp() == null)
                unread++;
        }
*/

