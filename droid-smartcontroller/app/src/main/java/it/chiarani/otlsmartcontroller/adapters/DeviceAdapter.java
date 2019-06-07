package it.chiarani.otlsmartcontroller.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import it.chiarani.otlsmartcontroller.R;
import it.chiarani.otlsmartcontroller.api.AuthBodyRetrofitModel;
import it.chiarani.otlsmartcontroller.api.OTLAPI;
import it.chiarani.otlsmartcontroller.api.RetrofitAPI;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.OTLDeviceEntity;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.User;
import it.chiarani.otlsmartcontroller.helpers.Config;
import it.chiarani.otlsmartcontroller.helpers.DeviceHelper;
import it.chiarani.otlsmartcontroller.helpers.DeviceTypes;
import it.chiarani.otlsmartcontroller.viewmodels.UserViewModel;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    private User    mUser;
    private Context context;
    private int     roomIndex;
    private UserViewModel userViewModel;
    private int isSet = 0;

    public DeviceAdapter(User mUser, int roomIndex, UserViewModel userViewModel) {
        this.mUser     = mUser;
        this.roomIndex = roomIndex;
        this.userViewModel = userViewModel;
    }

    @NonNull
    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        this.context = parent.getContext();

        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout  rl;
        LinearLayout    ll;
        TextView        title, devStatus;
        ImageView       img;

        ViewHolder(View v) {
            super(v);
            rl          = v.findViewById(R.id.item_device_rl);
            title       = v.findViewById(R.id.item_device_title);
            img         = v.findViewById(R.id.item_device_img);
            devStatus   = v.findViewById(R.id.item_device_status);
            ll          = v.findViewById(R.id.item_device_ll_qta_device);

            v.setOnClickListener(this);
        }

        @SuppressLint("CheckResult")
        @Override
        public void onClick(View v) {



            RetrofitAPI mRetrofitAPI = OTLAPI.getInstance();

            AuthBodyRetrofitModel authBodyRetrofitModel = new AuthBodyRetrofitModel();
            authBodyRetrofitModel.setClientUsername(Config.CLIENT_USERNAME);

            mRetrofitAPI.auth(authBodyRetrofitModel)
                    .take(1)
                    .flatMap(authRetrofitModel -> {
                        String token = authRetrofitModel.getToken();
                        int isSet = 0;
                        if(mUser.otlRoomsList.get(roomIndex).devices.get(0).deviceStatus == 0)
                        {
                            isSet = 1;
                        }
                        else
                        {
                            isSet = 0;
                        }
                        return mRetrofitAPI.controlDevice(token, "kitchen$light$lampada-sala", isSet == 1 ? "ON" : "OFF");
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe( x -> {
                        Toast.makeText(v.getContext(), x.getMessage(), Toast.LENGTH_LONG).show();

                        User tmpU = mUser;
                        List<OTLDeviceEntity> tmpDevices    = new ArrayList<>();
                        OTLDeviceEntity deviceEntity        = new OTLDeviceEntity();
                        deviceEntity.deviceDescription  = mUser.otlRoomsList.get(roomIndex).devices.get(0).deviceType;
                        if(mUser.otlRoomsList.get(roomIndex).devices.get(0).deviceStatus == 0)
                        {
                            deviceEntity.deviceStatus = 1;
                        }
                        else
                        {
                            deviceEntity.deviceStatus = 0;
                        }

                        deviceEntity.deviceType = mUser.otlRoomsList.get(roomIndex).devices.get(0).deviceType;
                        deviceEntity.deviceName = mUser.otlRoomsList.get(roomIndex).devices.get(0).deviceName;
                        tmpDevices.add(deviceEntity);

                        tmpU.otlRoomsList.get(0).devices = tmpDevices;

                        //  user.otlRoomsList.get(0).devices.get(this.getAdapterPosition()).deviceStatus = 0;
                        userViewModel.insertUser(tmpU)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread()).subscribe();
                    });
        }
    }


    @Override
    public void onBindViewHolder(@NonNull DeviceAdapter.ViewHolder holder, int position) {
        DeviceTypes type = DeviceHelper.parseType(mUser.otlRoomsList.get(roomIndex).devices.get(position).deviceType);
        holder.img.setImageDrawable(context.getResources().getDrawable(DeviceHelper.getResource(type)));
        holder.title.setText(mUser.otlRoomsList.get(roomIndex).devices.get(position).deviceName);
        holder.devStatus.setText(mUser.otlRoomsList.get(roomIndex).devices.get(position).deviceStatus == 1 ? " ON " : " OFF ");

        if( mUser.otlRoomsList.get(roomIndex).devices.get(position).deviceStatus == 1 ) {
            holder.ll.setBackground(context.getResources().getDrawable(R.drawable.item_device_background_on));
        }
        if( mUser.otlRoomsList.get(roomIndex).devices.get(position).deviceStatus == 0 ) {
            holder.ll.setBackground(context.getResources().getDrawable(R.drawable.item_device_background_off));
        }
    }

    @Override
    public int getItemCount() {
        return mUser.otlRoomsList.get(roomIndex).devices.size();
    }
}
