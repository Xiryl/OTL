package it.chiarani.otl.control;

import java.util.List;

import it.chiarani.otl.ServerRepository;
import it.chiarani.otl.retrofit_model.RetrofitAuth;
import it.chiarani.otl.retrofit_model.RetrofitAuthRes;
import it.chiarani.otl.retrofit_model.RetrofitDiscover;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitAPIClient {
    @GET("api/control/bedroom/sonoff-1/ON")
    Call<ServerRepository> chiamataOn(@Header("Authorization") String authHeader);

    @GET("api/control/bedroom/sonoff-1/OFF")
    Call<ServerRepository> chiamataOff(@Header("Authorization") String authHeader);

    @GET("api/discovery")
    Call<RetrofitDiscover> chiamataDiscover(@Header("Authorization") String authHeader);

    @POST("api/auth")
    Call<RetrofitAuthRes> chiamataAuth(@Body RetrofitAuth body);
}
