package it.chiarani.otl.retrofit_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GET_RetrofitDiscoverResponseModel {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private RetrofitDevicesModel message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public RetrofitDevicesModel getMessage() {
        return message;
    }

    public void setMessage(RetrofitDevicesModel message) {
        this.message = message;
    }
}
