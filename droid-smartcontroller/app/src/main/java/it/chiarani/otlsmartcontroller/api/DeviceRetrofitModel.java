package it.chiarani.otlsmartcontroller.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceRetrofitModel {

    @SerializedName("devname")
    @Expose
    private String devname;
    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("topic")
    @Expose
    private String topic;

    public String getDevname() {
        return devname;
    }

    public void setDevname(String devname) {
        this.devname = devname;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

}