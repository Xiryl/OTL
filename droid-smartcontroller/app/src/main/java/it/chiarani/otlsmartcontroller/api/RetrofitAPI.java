package it.chiarani.otlsmartcontroller.api;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("auth")
    Observable<AuthRetrofitModel> auth(@Body AuthBodyRetrofitModel body);

    @GET("discovery")
    Observable<DiscoveryRetrofitModel> discovery(@Header("Authorization") String authToken);

    @GET("control/bedroom/kitchen$light$lampada-sala/ON")
    Observable<DeviceControlRetrofitModel> controlDevice(@Header("Authorization") String authToken);
}
