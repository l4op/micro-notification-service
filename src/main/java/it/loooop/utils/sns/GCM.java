package it.loooop.utils.sns;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GCM {
    @JsonProperty("notification")
    private Notification notification;

    @JsonProperty("data")
    private Data data;

    public GCM() {}


    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}

