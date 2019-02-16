package it.chiarani.otl.retrofit_model;


import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class RetrofitAuth {

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