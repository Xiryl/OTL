package it.chiarani.otlsmartcontroller.controllers;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import it.chiarani.otlsmartcontroller.api.DiscoveryRetrofitModel;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.OTLDeviceEntity;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.OTLRoomsEntity;
import it.chiarani.otlsmartcontroller.viewmodels.UserViewModel;

public class DiscoveryInitialization implements Observer<DiscoveryRetrofitModel> {

    private UserViewModel mUserViewModel;

    public DiscoveryInitialization(UserViewModel userViewModel) {
        this.mUserViewModel = userViewModel;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void onNext(DiscoveryRetrofitModel discoveryRetrofitModel) {
        List<OTLDeviceEntity> tmpDevices    = new ArrayList<>();
        List<OTLRoomsEntity> tmpRoomsEntity = new ArrayList<>();
        OTLDeviceEntity deviceEntity        = new OTLDeviceEntity();
        OTLRoomsEntity roomsEntity          = new OTLRoomsEntity();

        String roomName = discoveryRetrofitModel.getMessage().getDevices().get(0).getDevname().split("\\$")[0];

        deviceEntity.deviceDescription  = discoveryRetrofitModel.getMessage().getDevices().get(0).getDevname();
        deviceEntity.deviceStatus       = discoveryRetrofitModel.getMessage().getDevices().get(0).getState();
        deviceEntity.deviceName         = discoveryRetrofitModel.getMessage().getDevices().get(0).getDevname().split("\\$")[1];
        tmpDevices.add(deviceEntity);

        roomsEntity.roomName = roomName;
        roomsEntity.devices  = tmpDevices;
        tmpRoomsEntity.add(roomsEntity);

        mUserViewModel.getUser()
                .take(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( user -> {
                    user.otlRoomsList = tmpRoomsEntity;
                    //bindRecyclerView(user);

                   mUserViewModel.insertUser(user)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe();
                });
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
