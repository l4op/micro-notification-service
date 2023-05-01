package it.loooop.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.loooop.Send;
import it.loooop.utils.sns.Data;
import it.loooop.utils.sns.GCM;
import it.loooop.utils.sns.GCMPush;
import it.loooop.utils.sns.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PushMessageUtils {

    static Logger logger = LoggerFactory.getLogger(PushMessageUtils.class);

    private static String getGcmMessage(String title, String text) {
        ObjectMapper mapper = new ObjectMapper();

        /*
        GCMPush push = new GCMPush();

        GCM gcm = new GCM();

        Data data = new Data();
        data.setTime_to_live(3600);
        data.setCollapse_key("deals");

        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setBody(text);
        gcm.setNotification(notification);
        gcm.setData(data);
        push.setGcm(gcm);
        push.setDef("GCM");

        try {
            String stringPush = mapper.writeValueAsString(push);
            logger.info("GCM: " + stringPush);
            return mapper.writeValueAsString(push);
        } catch (JsonProcessingException e) {
            logger.atError().setCause(e).log("ERROR in jackson");
        }

        return "";
*/
        /*
        {
  "GCM": "{\"notification\": { \"body\": \"Sample message for Android or iOS endpoints\", \"title\":\"TitleTest\" },\"data\":{\"time_to_live\": 3600,\"collapse_key\":\"deals\"}}"
}
         */

        //return "{\"GCM\":\\\"{ \\\"notification\\\": { \\\"body\\\": \\\"" + text + "\\\", \\\"title\\\":\\\"" + title + "\\\" }}\\\"}";
        //return "{\"data\":{\"title\":\"" + title + "\",\"message\":\"" + text + "\"}}";


        return "{\"GCM\": \"{\\\"notification\\\": { \\\"body\\\": \\\""+ text +"\\\", \\\"title\\\":\\\""+ title +"\\\" },\\\"data\\\":{\\\"time_to_live\\\": 3600,\\\"collapse_key\\\":\\\"deals\\\"}}\"}";
    }

    private static String getApnsMessage(String title, String text) {
        return "{\"APNS\": \\\"{\\\"aps\\\":{\\\"alert\\\": {\\\"title\\\":\\\"" + title + "\\\",\\\"body\\\":\\\"" + text + "\\\"}}}\\\"}";
    }

    public static String getCorrectMessageFormat(String type, String title, String text) {
        if("IOS".equals(type.toUpperCase()))
            return getApnsMessage(title, text);
        else
            return getGcmMessage(title, text);
    }
}
