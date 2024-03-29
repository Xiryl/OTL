package it.chiarani.otlsmartcontroller.controllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import it.chiarani.otlsmartcontroller.R;
import it.chiarani.otlsmartcontroller.api.DiscoveryRetrofitModel;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.OTLDeviceEntity;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.OTLRoomsEntity;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.User;
import it.chiarani.otlsmartcontroller.exceptions.NoConnectionException;
import it.chiarani.otlsmartcontroller.helpers.DeviceHelper;
import it.chiarani.otlsmartcontroller.helpers.DeviceTypes;
import it.chiarani.otlsmartcontroller.helpers.RoomHelper;
import it.chiarani.otlsmartcontroller.helpers.RoomTypes;
import it.chiarani.otlsmartcontroller.viewmodels.UserViewModel;

public class DiscoveryInitialization extends ContextWrapper implements Observer<DiscoveryRetrofitModel> {

    private UserViewModel mUserViewModel;

    public DiscoveryInitialization(Context base, UserViewModel mUserViewModel) {
        super(base);
        this.mUserViewModel = mUserViewModel;
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

        String roomType   = discoveryRetrofitModel.getMessage().getDevices().get(0).getDevname().split("\\$")[0];
        String deviceType = discoveryRetrofitModel.getMessage().getDevices().get(0).getDevname().split("\\$")[1];
        String deviceName = discoveryRetrofitModel.getMessage().getDevices().get(0).getDevname().split("\\$")[2];

        deviceEntity.deviceDescription  = discoveryRetrofitModel.getMessage().getDevices().get(0).getDevname();
        deviceEntity.deviceStatus       = discoveryRetrofitModel.getMessage().getDevices().get(0).getState();
        deviceEntity.deviceType         = deviceType;
        deviceEntity.deviceName         = deviceName;
        tmpDevices.add(deviceEntity);

        roomsEntity.roomType = roomType;
        roomsEntity.devices  = tmpDevices;
        tmpRoomsEntity.add(roomsEntity);

        mUserViewModel.getUser()
                .take(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    user.otlRoomsList = tmpRoomsEntity;
                    //bindRecyclerView(user);

                    mUserViewModel.insertUser(user)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe();
                });
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof java.net.ConnectException) {
            Toast.makeText(this, "ERRORE: Controlla la tua connessione..", Toast.LENGTH_LONG).show();
        } else if (e instanceof java.net.SocketTimeoutException) {
            Toast.makeText(this, "ERRORE: Il server sembra non rispondere.l", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "ERRORE: Mentre caricavo i dati qualcosa è andato storto..", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onComplete() {

    }
}
