package it.chiarani.otlsmartcontroller.api;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitAPI {
    @POST("auth")
    Observable<AuthRetrofitModel> auth(@Body AuthBodyRetrofitModel body);

    @GET("discovery")
    Observable<DiscoveryRetrofitModel> discovery(@Header("Authorization") String authToken);

    //@GET("control/bedroom/kitchen$light$lampada-sala/ON")
    @GET("control/bedroom/{device}/{newStatus}")
    Observable<DeviceControlRetrofitModel> controlDevice(@Header("Authorization") String authToken, @Path("device") String device, @Path("newStatus") String newStatus);
}
