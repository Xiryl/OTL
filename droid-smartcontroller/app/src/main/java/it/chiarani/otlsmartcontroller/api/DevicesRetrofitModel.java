package it.chiarani.otlsmartcontroller.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DevicesRetrofitModel {
    @SerializedName("devices")
    @Expose
    private List<DeviceRetrofitModel> devices = null;

    public List<DeviceRetrofitModel> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceRetrofitModel> devices) {
        this.devices = devices;
    }

}