package it.loooop;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.loooop.exception.ValidationException;
import it.loooop.model.App;
import it.loooop.model.Device;
import it.loooop.model.Notification;
import it.loooop.request.SendRequest;
import it.loooop.response.GenericResponse;
import it.loooop.response.SendResponse;
import it.loooop.service.AppService;
import it.loooop.service.DeviceService;
import it.loooop.service.NotificationService;
import it.loooop.service.SnsService;
import it.loooop.utils.Mapper;
import it.loooop.utils.PushMessageUtils;
import it.loooop.utils.Utils;
import it.loooop.utils.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Send implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    Logger logger = LoggerFactory.getLogger(Send.class);
    ObjectMapper mapper = new ObjectMapper();
    DeviceService deviceService = new DeviceService();
    AppService appService = new AppService();
    NotificationService notificationService = new NotificationService();
    SnsService snsService = new SnsService();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        try {
            SendRequest request = mapper.readValue(event.getBody(), SendRequest.class);

            logger.info(request.toString());

            Validation.validateSendRequest(request);

            Notification notification = null;
            String messageId = null;

            App appFound = appService.getApp(request.getAppId());

            if (appFound == null) {
                logger.atError().log("Error = APP_NOT_FOUND");
                return Utils.buildResponse("KO", new GenericResponse("KO", "APP_NOT_FOUND").toString());
            }

            //check if SMS
            if ("SMS".equals(request.getType())) {
                //SMS
                //Send direct to mobile number
                notification = Mapper.sendRequestToSms(request);

                try {
                    messageId = snsService.sendSms(request.getMobileNumber(), request.getMessage(), appFound.getAppId());
                } catch (Exception e) {
                    logger.atError().setCause(e).log("Exception = Exception Message = {}", e.getMessage());
                    return Utils.buildResponse("KO", new GenericResponse("KO", "SMS_ERROR").toString());
                }
            } else {
                //PUSH
                //Search for existing device
                Device deviceFound = deviceService.getDevice(request.getDeviceId());

                if (deviceFound != null) {

                    notification = Mapper.sendRequestToPush(request,
                            PushMessageUtils.getCorrectMessageFormat(request.getType(), request.getTitle(), request.getMessage()),
                            mapper.writeValueAsString(deviceFound)
                    );

                    try {
                        messageId = snsService.sendPush(notification, deviceFound, appFound);
                    } catch (Exception e) {
                        logger.atError().setCause(e).log("Exception = Exception Message = {}", e.getMessage());
                        return Utils.buildResponse("KO", new GenericResponse("KO", "PUSH_ERROR").toString());
                    }

                } else {
                    logger.atError().log("Error = DEVICE_NOT_FOUND");
                    return Utils.buildResponse("KO", new GenericResponse("KO", "DEVICE_NOT_FOUND").toString());
                }
            }

            //save notification in notification table
            notification.setMessageId(messageId);
            notificationService.persistNotification(notification);

            return Utils.buildResponse("OK", mapper.writeValueAsString(
                    new SendResponse("OK", "Notification sent", notification.getNotificationId(), notification.getInsertTimestamp())));

        } catch (ValidationException e1) {
            logger.atError().setCause(e1).log("Exception = ValidationException Code = {} Message = {}", e1.getCode(), e1.getMessage());
            return Utils.buildResponse("KO", new GenericResponse("KO", e1.getMessage()).toString());
        } catch (JsonProcessingException e2) {
            logger.atError().setCause(e2).log("Exception = JsonProcessingException Message = {}", e2.getMessage());
            return Utils.buildResponse("KO", new GenericResponse("KO", e2.getMessage()).toString());
        } catch (Exception e) {
            logger.atError().setCause(e).log("Exception = Exception Message = {}", e.getMessage());
            return Utils.buildResponse("KO", new GenericResponse("KO", e.getMessage()).toString());
        }

    }
}
