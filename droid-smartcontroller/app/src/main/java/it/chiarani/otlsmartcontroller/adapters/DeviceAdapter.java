package it.chiarani.otlsmartcontroller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import it.chiarani.otlsmartcontroller.R;
import it.chiarani.otlsmartcontroller.api.AuthBodyRetrofitModel;
import it.chiarani.otlsmartcontroller.api.OTLAPI;
import it.chiarani.otlsmartcontroller.api.RetrofitAPI;
import it.chiarani.otlsmartcontroller.controllers.DiscoveryInitialization;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.User;
import it.chiarani.otlsmartcontroller.helpers.Config;
import it.chiarani.otlsmartcontroller.helpers.DeviceHelper;
import it.chiarani.otlsmartcontroller.helpers.DeviceTypes;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    private User    mUser;
    private Context context;
    private int     roomIndex;

    public DeviceAdapter(User mUser, int roomIndex) {
        this.mUser     = mUser;
        this.roomIndex = roomIndex;
    }

    @NonNull
    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        this.context = parent.getContext();

        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout rl;
        TextView title, devStatus;
        ImageView img;

        ViewHolder(View v) {
            super(v);
            rl          = v.findViewById(R.id.item_device_rl);
            title       = v.findViewById(R.id.item_device_title);
            img         = v.findViewById(R.id.item_device_img);
            devStatus   = v.findViewById(R.id.item_device_status);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Toast.makeText(v.getContext(), "invio", Toast.LENGTH_LONG).show();
            RetrofitAPI mRetrofitAPI = OTLAPI.getInstance();

            AuthBodyRetrofitModel authBodyRetrofitModel = new AuthBodyRetrofitModel();
            authBodyRetrofitModel.setClientUsername(Config.CLIENT_USERNAME);

            mRetrofitAPI.auth(authBodyRetrofitModel)
                    .take(1)
                    .flatMap(authRetrofitModel -> {
                        String token = authRetrofitModel.getToken();
                        return mRetrofitAPI.controlDevice(token);
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe( x -> {
                        x.getMessage();
                    });

        }
    }


    @Override
    public void onBindViewHolder(@NonNull DeviceAdapter.ViewHolder holder, int position) {
        DeviceTypes type = DeviceHelper.parseType(mUser.otlRoomsList.get(roomIndex).devices.get(position).deviceType);
        holder.img.setImageDrawable(context.getResources().getDrawable(DeviceHelper.getResource(type)));
        holder.title.setText(mUser.otlRoomsList.get(roomIndex).devices.get(position).deviceName);
        holder.devStatus.setText(mUser.otlRoomsList.get(roomIndex).devices.get(position).deviceStatus == 1 ? " ON " : " OFF ");
    }

    @Override
    public int getItemCount() {
        return mUser.otlRoomsList.get(roomIndex).devices.size();
    }
}
