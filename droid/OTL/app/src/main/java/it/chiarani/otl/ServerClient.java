package it.chiarani.otl;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ServerClient {
    @GET("api/control/bedroom/sonoff-1/ON")
    Call<ServerRepository> chiamataOn(@Header("Authorization") String authHeader);

    @GET("api/control/bedroom/sonoff-1/OFF")
    Call<ServerRepository> chiamataOff(@Header("Authorization") String authHeader);
}
