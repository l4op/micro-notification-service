package it.loooop.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendRequest {

    //PartitionKey
    @JsonProperty("identificationId")
    private String identificationId;
    @JsonProperty("appId")
    private String appId;
    @JsonProperty("type")
    private String type;

    //Attributes
    @JsonProperty("title")
    private String title;
    @JsonProperty("message")
    private String message;
    @JsonProperty("mobileNumber")
    private String mobileNumber; //with international prefix

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
        return type.toUpperCase();
    }

    public void setType(String type) {
        this.type = type.toUpperCase();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String toString(){
        return "Object = SendRequest identificationId = "+this.identificationId+
                " appId = "+this.appId+
                " type = "+this.type+
                " title = "+this.title+
                " message = "+this.message+
                " mobileNumber = "+this.mobileNumber;
    }

    public String getDeviceId() {
        return this.identificationId+"#"+this.appId+"#active";
    }
}
