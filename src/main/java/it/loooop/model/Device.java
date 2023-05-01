package it.loooop.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.io.Serializable;

@DynamoDbBean
public class Device implements Serializable {
    private static final long serialVersionUID = 1857157464650282940L;
    private String deviceId = null; //identificationId_appId_status
    private String type = null;
    private String token = null;
    private String deviceInfo = null;
    private String updateTimestamp = null;

    public Device() {}

    public Device(String deviceId, String type, String token, String deviceInfo, String updateTimestamp) {
        this.deviceId = deviceId;
        this.type = type;
        this.token = token;
        this.deviceInfo = deviceInfo;
        this.updateTimestamp = updateTimestamp;
    }

    @DynamoDbPartitionKey
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(String updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
}
