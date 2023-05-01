package it.loooop.utils.sns;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

    public Data() {}

    @JsonProperty("time_to_live")
    private Integer time_to_live;

    @JsonProperty("collapse_key")
    private String collapse_key;


    public Integer getTime_to_live() {
        return time_to_live;
    }

    public void setTime_to_live(Integer time_to_live) {
        this.time_to_live = time_to_live;
    }

    public String getCollapse_key() {
        return collapse_key;
    }

    public void setCollapse_key(String collapse_key) {
        this.collapse_key = collapse_key;
    }
}
