package it.chiarani.otlsmartcontroller.views;

import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import it.chiarani.otlsmartcontroller.R;
import it.chiarani.otlsmartcontroller.adapters.RoomsAdapter;
import it.chiarani.otlsmartcontroller.api.AuthBodyRetrofitModel;
import it.chiarani.otlsmartcontroller.api.AuthRetrofitModel;
import it.chiarani.otlsmartcontroller.api.DiscoveryRetrofitModel;
import it.chiarani.otlsmartcontroller.api.RetrofitAPI;
import it.chiarani.otlsmartcontroller.databinding.ActivityMainBinding;
import it.chiarani.otlsmartcontroller.db.Injection;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.OTLDeviceEntity;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.OTLRoomsEntity;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.User;
import it.chiarani.otlsmartcontroller.helpers.Config;
import it.chiarani.otlsmartcontroller.helpers.UnsafeHttpClient;
import it.chiarani.otlsmartcontroller.viewmodels.UserViewModel;
import it.chiarani.otlsmartcontroller.viewmodels.ViewModelFactory;
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
                .flatMap((Function<AuthRetrofitModel, Observable<DiscoveryRetrofitModel>>) authRetrofitModel -> {
                    String token = authRetrofitModel.getToken();
                    return retrofitAPI.discovery(token);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DiscoveryRetrofitModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DiscoveryRetrofitModel discoveryRetrofitModel) {
                        List<OTLDeviceEntity> tmpDevices = new ArrayList<>();
                        List<OTLRoomsEntity> tmpRoomsEntity = new ArrayList<>();
                        OTLDeviceEntity deviceEntity = new OTLDeviceEntity();
                        OTLRoomsEntity roomsEntity = new OTLRoomsEntity();

                        String roomName = discoveryRetrofitModel.getMessage().getDevices().get(0).getDevname().split("/")[0];

                        deviceEntity.deviceDescription = discoveryRetrofitModel.getMessage().getDevices().get(0).getDevname();
                      //  deviceEntity.deviceStatus = discoveryRetrofitModel.getMessage().getDevices().get(0).getState() == 1;
                        deviceEntity.deviceName = discoveryRetrofitModel.getMessage().getDevices().get(0).getDevname().split("/")[1];
                        tmpDevices.add(deviceEntity);

                        roomsEntity.roomName = roomName;
                        roomsEntity.devices = tmpDevices;

                        tmpRoomsEntity.add(roomsEntity);

                        mDisposable.add(mUserViewModel.getUser()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe( user -> {
                                    user.otlRoomsList = tmpRoomsEntity;
                                    //bindRecyclerView(user);

                                }));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        updateUI();
    }


    @Override
    protected void onStop() {
        super.onStop();

        // clear all the subscriptions
        mDisposable.clear();
    }


    private void updateUI() {
        mDisposable.add(mUserViewModel.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( user -> {
                    binding.mainActivityTxtWelcome.setText(String.format("%s %s",getResources().getString(R.string.main_welcome), user.userName.split(" ")[0]));
                    Glide.with(this).load(user.userPicture).into(binding.mainActivityImgUser);
                }));
    }

    private void bindRecyclerView(User userProfileEntity) {
        // qui observo
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
