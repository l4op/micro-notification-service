package it.loooop.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReadRequest {

    @JsonProperty("notificationId")
    private String notificationId;

    @JsonProperty("insertTimestamp")
    private String insertTimestamp;

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getInsertTimestamp() {
        return insertTimestamp;
    }

    public void setInsertTimestamp(String insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
    }

    @Override
    public String toString(){
        return "Object = ReadRequest notificationId = "+this.notificationId+
                " insertTimestamp = "+this.insertTimestamp;
    }
}
