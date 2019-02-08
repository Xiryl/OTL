package it.chiarani.otl.retrofit_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RetrofitDiscover {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private RetrofitDevices message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public RetrofitDevices getMessage() {
        return message;
    }

    public void setMessage(RetrofitDevices message) {
        this.message = message;
    }
}
