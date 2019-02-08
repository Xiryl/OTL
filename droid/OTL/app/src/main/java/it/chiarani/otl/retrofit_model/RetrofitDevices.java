package it.chiarani.otl.retrofit_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RetrofitDevices {
    @SerializedName("devices")
    @Expose
    private List<RetrofitDevice> devices = null;

    public List<RetrofitDevice> getDevices() {
        return devices;
    }

    public void setDevices(List<RetrofitDevice> devices) {
        this.devices = devices;
    }
}
