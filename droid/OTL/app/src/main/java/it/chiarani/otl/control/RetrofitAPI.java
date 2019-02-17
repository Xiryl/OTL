package it.chiarani.otl.control;

import it.chiarani.otl.retrofit_model.GET_RetrofitControlResponseModel;
import it.chiarani.otl.retrofit_model.POST_RetrofitAuthRequestModel;
import it.chiarani.otl.retrofit_model.GET_RetrofitAuthResponseModel;
import it.chiarani.otl.retrofit_model.GET_RetrofitDiscoverResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitAPI {

    @GET("api/control/{topic}/{device}/{value}")
    Call<GET_RetrofitControlResponseModel> APIControlDevice(
                @Header("Authorization") String authHeader,
                @Path(value = "topic", encoded = true) String topic,
                @Path(value = "device", encoded = true) String device,
                @Path(value = "value", encoded = true) String value
            );

    @GET("api/discovery")
    Call<GET_RetrofitDiscoverResponseModel> APIDiscovery(@Header("Authorization") String authHeader);

    @POST("api/auth")
    Call<GET_RetrofitAuthResponseModel> APIAuth(@Body POST_RetrofitAuthRequestModel body);
}
