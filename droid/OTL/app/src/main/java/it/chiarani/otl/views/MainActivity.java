package it.chiarani.otl.views;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import it.chiarani.otl.R;
import it.chiarani.otl.control.DeviceCommander;
import it.chiarani.otl.control.RetrofitAPI;
import it.chiarani.otl.helper.APITypes;
import it.chiarani.otl.retrofit_model.GET_RetrofitControlResponseModel;
import it.chiarani.otl.adapters.DeviceAdapter;
import it.chiarani.otl.adapters.RoomsAdapter;
import it.chiarani.otl.databinding.ActivityMainBinding;
import it.chiarani.otl.helper.Config;
import it.chiarani.otl.model.Device;
import it.chiarani.otl.model.HouseRoom;
import it.chiarani.otl.retrofit_model.POST_RetrofitAuthRequestModel;
import it.chiarani.otl.retrofit_model.GET_RetrofitAuthResponseModel;
import it.chiarani.otl.retrofit_model.RetrofitDeviceModel;
import it.chiarani.otl.retrofit_model.RetrofitDevicesModel;
import it.chiarani.otl.retrofit_model.GET_RetrofitDiscoverResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static it.chiarani.otl.control.DeviceCommander.getUnsafeOkHttpClient;


public class MainActivity extends BaseActivity implements DeviceAdapter.ClickListener {

    private final String            TAG = this.getClass().getSimpleName();
    private ActivityMainBinding     binding;
    List<Device> devices = new ArrayList<>();
    List<HouseRoom> rooms = new ArrayList<>();
    DeviceAdapter deviceAdapter;
    RoomsAdapter roomsAdapter;
    Retrofit retrofit;
    RetrofitAPI retrofitAPIClient;
    Button accendi, spegni;


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

        Log.d(TAG, "Launching Main Activity");

        // bottombar
        binding.mainActivityBottomappbar.replaceMenu(R.menu.bottom_menu);

        rooms.add(new HouseRoom("h", "Salotto", "Salotto", "m", "n", "s", true, true));
        rooms.add(new HouseRoom("h", "Cucina", "Cucina", "m", "n", "s", true, true));

        setListAdapters();

        retrofit = new Retrofit.Builder()
            .baseUrl(Config.MQTT_BROKER_ADDR)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient())
                .build();

        retrofitAPIClient = retrofit.create(RetrofitAPI.class);

        POST_RetrofitAuthRequestModel retrofitAuthRequestModel = new POST_RetrofitAuthRequestModel();
        retrofitAuthRequestModel.setClientUsername("op6-fabio");
        Call<GET_RetrofitAuthResponseModel> call1 = retrofitAPIClient.APIAuth(retrofitAuthRequestModel);
        String token = ";";
        call1.enqueue(new Callback<GET_RetrofitAuthResponseModel>() {
            @Override
            public void onResponse(Call<GET_RetrofitAuthResponseModel> call, Response<GET_RetrofitAuthResponseModel> response) {
                String ttt =response.body().getToken();


            }

            @Override
            public void onFailure(Call<GET_RetrofitAuthResponseModel> call, Throwable t) {

            }
        });


        Call<GET_RetrofitDiscoverResponseModel> retrofitDiscoverResponseModelCall = retrofitAPIClient.APIDiscovery(ttt);
        retrofitDiscoverResponseModelCall.enqueue(new Callback<GET_RetrofitDiscoverResponseModel>() {
            @Override
            public void onResponse(Call<GET_RetrofitDiscoverResponseModel> call, Response<GET_RetrofitDiscoverResponseModel> response) {


                RetrofitDevicesModel mex =response.body().getMessage();
                List<RetrofitDeviceModel> available_dev = mex.getDevices();

                for(RetrofitDeviceModel device : available_dev) {
                    if(device.getState() == 1) {
                        devices.add(new Device("id", device.getDevname(), device.getTopic(), "", "", "", true, true));
                    }
                    else
                    {
                        devices.add(new Device("id", device.getDevname(), device.getTopic(), "", "", "", false, true));
                    }
                }


                Toast.makeText(MainActivity.this, "Scan completed.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<GET_RetrofitDiscoverResponseModel> call, Throwable t) {
                int x = 1;
            }
        });

        /*
        Call<GET_RetrofitControlResponseModel> call_x = DeviceCommander.sendCommand(APITypes.DEVICE_ON,retrofitAPIClient, Config.TMP_TOKEN, devices.get(0));

        call_x.enqueue(new Callback<GET_RetrofitControlResponseModel>() {
            @Override
            public void onResponse(Call<GET_RetrofitControlResponseModel> call, Response<GET_RetrofitControlResponseModel> response) {
                int x = 1;
                return;
            }

            @Override
            public void onFailure(Call<GET_RetrofitControlResponseModel> call, Throwable t) {
                int x = 1;
            }
        });*/
    }

    private void setListAdapters() {
        binding.mainactivityRvDevices.setHasFixedSize(true);
        binding.mainactivityRvRooms.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManagerslot = new LinearLayoutManager(this);
        linearLayoutManagerslot.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.mainactivityRvDevices.setLayoutManager(linearLayoutManagerslot);

        LinearLayoutManager linearLayoutManagerslot_rooms = new LinearLayoutManager(this);
        linearLayoutManagerslot_rooms.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.mainactivityRvRooms.setLayoutManager(linearLayoutManagerslot_rooms);

        deviceAdapter = new DeviceAdapter(getApplicationContext(),this, devices);
        binding.mainactivityRvDevices.setAdapter(deviceAdapter);

        roomsAdapter = new RoomsAdapter(getApplicationContext(),null, rooms);
        binding.mainactivityRvRooms.setAdapter(roomsAdapter);
    }


    private void fabClickListener() {
        binding.mainactivityFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onClick(String status) {

        if(status.equals("OFF")) {

        }
        if(status.equals("ON")) {


        }

    }
}
