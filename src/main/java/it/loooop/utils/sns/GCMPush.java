package it.loooop.utils.sns;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GCMPush {

    @JsonProperty("default")
    private String def; //default is keyword
    @JsonProperty("GCM")
    private GCM gcm;

    public GCMPush() {}
    public String getDef() {
        return def;
    }
    public void setDef(String def) {
        this.def = def;
    }
    public GCM getGcm() {
        return gcm;
    }
    public void setGcm(GCM gcm) {
        this.gcm = gcm;
    }
}
