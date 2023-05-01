package it.loooop.utils;

import it.loooop.exception.ValidationException;
import it.loooop.request.ListRequest;
import it.loooop.request.PairRequest;
import it.loooop.request.ReadRequest;
import it.loooop.request.SendRequest;

public class Validation {

    public static void validateListRequest(ListRequest pr) {
        if(pr == null)
            throw new ValidationException("Request malformed","REQUEST_MALFORMED");

        if(pr.getIdentificationId() == null || pr.getIdentificationId().isEmpty())
            throw new ValidationException("[identificationId] cannot be null or empty","REQUIRED_FILED");

        if (pr.getAppId() == null || pr.getAppId().isEmpty())
            throw new ValidationException("[appId] cannot be null or empty","REQUIRED_FILED");

        if (pr.getType() == null || pr.getType().isEmpty())
            throw new ValidationException("[type] cannot be null or empty","REQUIRED_FILED");

        if (pr.getFrom() == null || pr.getFrom().isEmpty())
            throw new ValidationException("[from] cannot be null or empty","REQUIRED_FILED");

        if (pr.getTo() == null || pr.getTo().isEmpty())
            throw new ValidationException("[to] cannot be null or empty","REQUIRED_FILED");
    }

    public static void validatePairRequest(PairRequest pr) {
        if(pr == null)
            throw new ValidationException("Request malformed","REQUEST_MALFORMED");

        if(pr.getIdentificationId() == null || pr.getIdentificationId().isEmpty())
            throw new ValidationException("[identificationId] cannot be null or empty","REQUIRED_FILED");

        if (pr.getAppId() == null || pr.getAppId().isEmpty())
            throw new ValidationException("[appId] cannot be null or empty","REQUIRED_FILED");

        if (pr.getToken() == null || pr.getToken().isEmpty())
            throw new ValidationException("[deviceToken] cannot be null or empty","REQUIRED_FILED");

        if (pr.getType() == null || pr.getType().isEmpty())
            throw new ValidationException("[type] cannot be null or empty","REQUIRED_FILED");

        if (pr.getDeviceInfo() == null || pr.getDeviceInfo().isEmpty())
            throw new ValidationException("[deviceInfo] cannot be null or empty","REQUIRED_FILED");
    }

    public static void validateReadRequest(ReadRequest pr) {
        if(pr == null)
            throw new ValidationException("Request malformed","REQUEST_MALFORMED");

        if(pr.getNotificationId() == null || pr.getNotificationId().isEmpty())
            throw new ValidationException("[notificationId] cannot be null or empty","REQUIRED_FILED");

        if (pr.getInsertTimestamp() == null || pr.getInsertTimestamp().isEmpty())
            throw new ValidationException("[insertTimestamp] cannot be null or empty","REQUIRED_FILED");

    }

    public static void validateSendRequest(SendRequest pr) {
        if(pr == null)
            throw new ValidationException("Request malformed","REQUEST_MALFORMED");

        if(pr.getIdentificationId() == null || pr.getIdentificationId().isEmpty())
            throw new ValidationException("[identificationId] cannot be null or empty","REQUIRED_FILED");

        if (pr.getAppId() == null || pr.getAppId().isEmpty())
            throw new ValidationException("[appId] cannot be null or empty","REQUIRED_FILED");

        if (pr.getType() == null || pr.getType().isEmpty())
            throw new ValidationException("[type] cannot be null or empty","REQUIRED_FILED");

        if ("SMS".equals(pr.getType())) {
            if (pr.getMobileNumber() == null || pr.getMobileNumber().isEmpty())
                throw new ValidationException("[mobileNumber] cannot be null or empty", "REQUIRED_FILED");
        } else {
            if (pr.getTitle() == null || pr.getTitle().isEmpty())
                throw new ValidationException("[title] cannot be null or empty", "REQUIRED_FILED");
        }

        if (pr.getMessage() == null || pr.getMessage().isEmpty())
            throw new ValidationException("[message] cannot be null or empty","REQUIRED_FILED");
    }
}
