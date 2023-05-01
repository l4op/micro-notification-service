package it.loooop.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.io.Serializable;

@DynamoDbBean
public class Notification implements Serializable {
    private static final long serialVersionUID = 5614734662980509427L;
    private String notificationId = null; //identificationId_appId_type
    private String insertTimestamp = null;

    private String messageId = null; //messageId from SNS
    private String readTimestamp = null;
    private String message = null;
    private String device = null; //identificationId_appId_status

    public Notification () {}

    public Notification (String notificationId, String insertTimestamp, String message, String device) {
        this.notificationId = notificationId;
        this.insertTimestamp = insertTimestamp;
        this.message = message;
        this.device = device;
    }

    @DynamoDbPartitionKey
    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    @DynamoDbSortKey
    public String getInsertTimestamp() {
        return insertTimestamp;
    }

    public void setInsertTimestamp(String insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
    }

    public String getReadTimestamp() {
        return readTimestamp;
    }

    public void setReadTimestamp(String readTimestamp) {
        this.readTimestamp = readTimestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

}
