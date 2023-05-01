package it.loooop.utils;

import it.loooop.model.Device;
import it.loooop.model.Notification;
import it.loooop.request.PairRequest;
import it.loooop.request.SendRequest;

public class Mapper {
    public static Device pairRequestToDevice(PairRequest request) {
        return new Device(
                request.getIdentificationId()+"#"+request.getAppId()+"#active",
                request.getType(),
                request.getToken(),
                request.getDeviceInfo(),
                Utils.currentStringDate()
        );
    }

    public static Notification sendRequestToSms(SendRequest request) {
        return new Notification(
                request.getIdentificationId()+"#"+request.getAppId()+"#"+request.getType(),
                Utils.currentStringDate(),
                request.getMessage(),
                request.getMobileNumber()
        );
    }

    public static Notification sendRequestToPush(SendRequest request, String pushBody, String device) {
        return new Notification(
                request.getIdentificationId()+"#"+request.getAppId()+"#"+request.getType(),
                Utils.currentStringDate(),
                pushBody,
                device
        );
    }
}
