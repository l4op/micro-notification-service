package it.loooop.response;

public class PairResponse extends GenericResponse {

    public PairResponse(String status, String message, String deviceId) {
        super(status, message);
        this.deviceId = deviceId;
    }

    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
