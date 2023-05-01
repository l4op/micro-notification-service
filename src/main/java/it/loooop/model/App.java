package it.loooop.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.io.Serializable;

@DynamoDbBean
public class App implements Serializable {

    private static final long serialVersionUID = -3657280991435198479L;

    private String appId = null;
    private String snsArnAndroid = null;
    private String snsArnIos = null;

    public App () {}

    public App (String appId, String snsArnAndroid, String snsArnIos) {
        this.appId = appId;
        this.snsArnAndroid = snsArnAndroid;
        this.snsArnIos = snsArnIos;
    }

    @DynamoDbPartitionKey
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSnsArnAndroid() {
        return snsArnAndroid;
    }

    public void setSnsArnAndroid(String snsArnAndroid) {
        this.snsArnAndroid = snsArnAndroid;
    }

    public String getSnsArnIos() {
        return snsArnIos;
    }

    public void setSnsArnIos(String snsArnIos) {
        this.snsArnIos = snsArnIos;
    }
}
