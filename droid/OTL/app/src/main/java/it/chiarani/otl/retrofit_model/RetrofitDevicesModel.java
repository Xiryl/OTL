package it.chiarani.otl.retrofit_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RetrofitDevicesModel {
    @SerializedName("devices")
    @Expose
    private List<RetrofitDeviceModel> devices = null;

    public List<RetrofitDeviceModel> getDevices() {
        return devices;
    }

    public void setDevices(List<RetrofitDeviceModel> devices) {
        this.devices = devices;
    }
}
