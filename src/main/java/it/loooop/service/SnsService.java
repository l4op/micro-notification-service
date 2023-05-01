package it.loooop.service;

import it.loooop.model.App;
import it.loooop.model.Device;
import it.loooop.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsAsyncClient;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

import java.util.HashMap;
import java.util.Map;

public class SnsService {

    Logger logger = LoggerFactory.getLogger(NotificationService.class);
    Region region = Region.of(System.getenv("AWS_REGION"));

    private final SnsClient snsClient = SnsClient.builder()
            .region(region)
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build();

    private SnsAsyncClient snsAsyncClient = SnsAsyncClient.builder()
            .region(region)
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build();
    Map<String, String> snsPlatformMap = Map.of(
            "ANDROID", "AWS.SNS.MOBILE.GCM",
            "IOS", "AWS.SNS.MOBILE.APNS");


    public String sendSms(String mobileNumber, String text, String senderId) {

        Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();
        MessageAttributeValue.Builder builder = MessageAttributeValue.builder().dataType("String");
        smsAttributes.put("AWS.SNS.SMS.SMSType", builder.stringValue("Transactional").build());
        smsAttributes.put("AWS.SNS.SMS.SenderID", builder.stringValue(senderId).build());

        PublishRequest request = PublishRequest.builder()
                .message(text)
                .phoneNumber(mobileNumber)
                .messageAttributes(smsAttributes)
                .build();

        PublishResponse publishResponse = snsClient.publish(request);

        return publishResponse.messageId();

    }

    public String sendPush(Notification notification, Device device, App app) {

        String snsAppropriateArn = null;
        if ("IOS".equalsIgnoreCase(device.getType()))
            snsAppropriateArn = app.getSnsArnIos();
        else
            snsAppropriateArn = app.getSnsArnAndroid();

        CreatePlatformEndpointRequest endPointRequest = CreatePlatformEndpointRequest.builder()
                .platformApplicationArn(snsAppropriateArn)
                .token(device.getToken())
                .build();

        CreatePlatformEndpointResponse result = snsClient.createPlatformEndpoint(endPointRequest);

        PublishRequest request = PublishRequest.builder()
                .targetArn(result.endpointArn())
                .messageStructure("json")
                .message(notification.getMessage())
                .build();

        PublishResponse publishResponse = snsClient.publish(request);

        return publishResponse.messageId();

    }
}
