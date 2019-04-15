package it.chiarani.otlsmartcontroller.views;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import androidx.constraintlayout.solver.widgets.Flow;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ScheduledDirectPeriodicTask;
import io.reactivex.schedulers.Schedulers;
import it.chiarani.otlsmartcontroller.api.AuthBodyRetrofitModel;
import it.chiarani.otlsmartcontroller.api.AuthRetrofitModel;
import it.chiarani.otlsmartcontroller.api.DiscoveryRetrofitModel;
import it.chiarani.otlsmartcontroller.api.RetrofitAPI;
import it.chiarani.otlsmartcontroller.controllers.ApiAuthService;
import it.chiarani.otlsmartcontroller.db.Injection;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.OTLRoomsEntity;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.User;
import it.chiarani.otlsmartcontroller.R;
import it.chiarani.otlsmartcontroller.adapters.RoomsAdapter;
import it.chiarani.otlsmartcontroller.databinding.ActivityMainBinding;
import it.chiarani.otlsmartcontroller.helpers.Config;
import it.chiarani.otlsmartcontroller.helpers.UnsafeHttpClient;
import it.chiarani.otlsmartcontroller.viewmodels.UserViewModel;
import it.chiarani.otlsmartcontroller.viewmodels.ViewModelFactory;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private ViewModelFactory mViewModelFactory;
    private UserViewModel mUserViewModel;


    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void setActivityBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutID());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModelFactory = Injection.provideViewModelFactory(this);
        mUserViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel.class);

        RetrofitAPI retrofitAPI = buildRetrofitApi();

        AuthBodyRetrofitModel authBodyRetrofitModel = new AuthBodyRetrofitModel();
        authBodyRetrofitModel.setClientUsername("op6-fabio");

        retrofitAPI.auth(authBodyRetrofitModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AuthRetrofitModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AuthRetrofitModel authRetrofitModel) {
                        String token = authRetrofitModel.getToken();
                        retrofitAPI.discovery(token)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<DiscoveryRetrofitModel>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(DiscoveryRetrofitModel discoveryRetrofitModel) {
                                        String roomName = discoveryRetrofitModel.getMessage().getDevices().get(0).getDevname().split("/")[0];
                                        OTLRoomsEntity roomsEntity = new OTLRoomsEntity();
                                        roomsEntity.roomName = roomName;
                                        List<OTLRoomsEntity> tmpRoomsEntity = new ArrayList<>();
                                        tmpRoomsEntity.add(roomsEntity);

                                        mDisposable.add(mUserViewModel.getUser()
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe( user -> {
                                                    user.otlRoomsList = tmpRoomsEntity;
                                                    bindRecyclerView(user);

                                                }));
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
                        int x = 1;
                    }

                    @Override
                    public void onComplete() {
                        int x = 1;
                    }
                });


    }


    @Override
    protected void onStop() {
        super.onStop();

        // clear all the subscriptions
        mDisposable.clear();
    }



    // updateUI();

        /*
        Retrofit.Builder builder = new Retrofit.Builder();

        builder.baseUrl("https://156.54.213.27/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(UnsafeHttpClient.makeUnsafe())
                .build();

        Retrofit retrofit = builder.build();

        RetrofitAPI authRetrofitModel = retrofit.create(RetrofitAPI.class);

        AuthBodyRetrofitModel authBodyRetrofitModel = new AuthBodyRetrofitModel();
        authBodyRetrofitModel.setClientUsername("op6-fabio");


        Call<AuthRetrofitModel> call = authRetrofitModel.auth(authBodyRetrofitModel);


        call.enqueue(new Callback<AuthRetrofitModel>() {
            @Override
            public void onResponse(Call<AuthRetrofitModel> call, Response<AuthRetrofitModel> response) {
                String ttt =response.body().getToken();
        int x = 1;
            }

            @Override
            public void onFailure(Call<AuthRetrofitModel> call, Throwable t) {
                int x = 1;
            }
        });*/
/*
    private void updateUI() {

        authData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( () -> {
                    gotoMain();
                }, throwable -> {
                    Log.e(TAG, "Unable to update username", throwable);
                }));
        getViewModel().getUserData().observe(this, users -> {

            if(users == null) {
                updateErrorUI();
            }

            List<OTLRoomsEntity> roomsEntities = new ArrayList<>();
            OTLRoomsEntity room = new OTLRoomsEntity();
            room.roomName = "cucina";
            room.roomLocation = "kitchen";
            roomsEntities.add(room) ;

            users.get(0).otlRoomsList = roomsEntities;


            binding.mainActivityTxtWelcome.setText(String.format("Benvenuto %s", users.get(0).userName.split(" ")[0]));
            Glide.with(this).load(users.get(0).userPicture).into(binding.mainActivityImgUser);

            bindRecyclerView(users.get(0));
        });
    }

    private void updateErrorUI() {

    }
*/
    private void bindRecyclerView(User userProfileEntity) {
        LinearLayoutManager linearLayoutManagerslot = new LinearLayoutManager(this);
        linearLayoutManagerslot.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.mainActivityRecyclerviewRooms.setLayoutManager(linearLayoutManagerslot);

        RoomsAdapter roomsAdapter = new RoomsAdapter(userProfileEntity);
        binding.mainActivityRecyclerviewRooms.setAdapter(roomsAdapter);
    }



    private RetrofitAPI buildRetrofitApi() {
        return new Retrofit.Builder()
                    .baseUrl(Config.API_URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(UnsafeHttpClient.makeUnsafe())
                    .build()
                    .create(RetrofitAPI.class);
    }
}
