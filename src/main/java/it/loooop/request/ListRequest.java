package it.loooop.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListRequest {

    //PartitionKey
    @JsonProperty("identificationId")
    private String identificationId;
    @JsonProperty("appId")
    private String appId;
    @JsonProperty("type")
    private String type;

    //SortKey
    @JsonProperty("from")
    private String from;
    @JsonProperty("to")
    private String to;

    public String getIdentificationId() {
        return identificationId;
    }
    public void setIdentificationId(String identificationId) {
        this.identificationId = identificationId;
    }
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString(){
        return "Object = ListRequest identificationId = "+this.identificationId+
                " appId = "+this.appId+
                " type = "+this.type+
                " from = "+this.from+
                " to = "+this.to;
    }

    public String getSearchKey(){
        return this.identificationId+"#"+this.getAppId()+"#"+this.type;
    }

}
