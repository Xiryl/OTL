package it.chiarani.otlsmartcontroller.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthBodyRetrofitModel {

    @SerializedName("client_username")
    @Expose
    private String clientUsername;

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

}