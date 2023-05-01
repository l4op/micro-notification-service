package it.loooop.response;

public class SendResponse extends GenericResponse {

    public SendResponse(String status, String message, String notificationId, String insertTimestamp) {
        super(status, message);
        this.notificationId = notificationId;
        this.insertTimestamp = insertTimestamp;
    }

    private String notificationId;
    private String insertTimestamp;

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getInsertTimestamp() {
        return insertTimestamp;
    }

    public void setInsertTimestamp(String insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
    }
}
