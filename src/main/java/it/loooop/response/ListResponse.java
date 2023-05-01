package it.loooop.response;

import it.loooop.model.Notification;

import java.util.List;

public class ListResponse extends GenericResponse {

    public ListResponse(String status, String message, List<Notification> notificationList, Integer unread) {
        super(status, message);
        this.notificationList = notificationList;
        this.unread = unread;
    }

    private List<Notification> notificationList;
    private Integer unread;

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }
}