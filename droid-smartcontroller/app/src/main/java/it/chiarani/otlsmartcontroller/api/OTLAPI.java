package it.chiarani.otlsmartcontroller.api;

import android.content.Context;

import androidx.room.Room;
import it.chiarani.otlsmartcontroller.db.persistence.UserDatabase;
import it.chiarani.otlsmartcontroller.helpers.Config;
import it.chiarani.otlsmartcontroller.helpers.UnsafeHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class OTLAPI {
    private static volatile RetrofitAPI INSTANCE;

    public static RetrofitAPI getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitAPI.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Retrofit.Builder()
                            .baseUrl(Config.API_URL_BASE)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(UnsafeHttpClient.makeUnsafe())
                            .build()
                            .create(RetrofitAPI.class);
                }
            }
        }
        return INSTANCE;
    }
}
