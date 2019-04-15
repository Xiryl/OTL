package it.chiarani.otlsmartcontroller.controllers;

import io.reactivex.Flowable;
import it.chiarani.otlsmartcontroller.api.AuthBodyRetrofitModel;
import it.chiarani.otlsmartcontroller.api.AuthRetrofitModel;
import it.chiarani.otlsmartcontroller.api.RetrofitAPI;
import it.chiarani.otlsmartcontroller.helpers.UnsafeHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAuthService {
    private RetrofitAPI mRetrofitAPI;

    public ApiAuthService() {
        Retrofit.Builder builder = new Retrofit.Builder();

        builder.baseUrl("https://156.54.213.27/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(UnsafeHttpClient.makeUnsafe())
                .build();

        Retrofit retrofit = builder.build();

        mRetrofitAPI = retrofit.create(RetrofitAPI.class);
    }

    // return execution of auth
    public Flowable<AuthRetrofitModel> getAuthResult(AuthBodyRetrofitModel body)  {
      //  return mRetrofitAPI.auth(body);
        return null;
    }
}
