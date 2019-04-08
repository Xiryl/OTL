package it.chiarani.otlsmartcontroller.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("auth")
    Call<AuthRetrofitModel> auth(@Body AuthBodyRetrofitModel body);
}
