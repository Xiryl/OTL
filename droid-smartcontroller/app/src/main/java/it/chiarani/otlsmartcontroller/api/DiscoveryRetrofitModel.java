package it.chiarani.otlsmartcontroller.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscoveryRetrofitModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private DevicesRetrofitModel message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public DevicesRetrofitModel getMessage() {
        return message;
    }

    public void setMessage(DevicesRetrofitModel message) {
        this.message = message;
    }

}