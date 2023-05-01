package it.loooop.utils.sns;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Notification {
    @JsonProperty("title")
    private String title;
    @JsonProperty("body")
    private String body;



    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
