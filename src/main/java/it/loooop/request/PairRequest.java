package it.loooop.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PairRequest {

    //PartitionKey
    @JsonProperty("identificationId")
    private String identificationId;
    @JsonProperty("appId")
    private String appId;
    @JsonProperty("type")
    private String type;
    //Attributes
    @JsonProperty("token")
    private String token;
    @JsonProperty("deviceInfo")
    private String deviceInfo;

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
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getDeviceInfo() {
        return deviceInfo;
    }
    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    @Override
    public String toString(){
        return "Object = PairRequest identificationId = "+this.identificationId+
                " appId = "+this.appId+
                " type = "+this.type+
                " token = "+this.token+
                " deviceInfo = "+this.deviceInfo;
    }
}
