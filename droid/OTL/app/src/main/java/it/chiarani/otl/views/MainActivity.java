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
import it.chiarani.otl.control.RetrofitAPIClient;
import it.chiarani.otl.ServerRepository;
import it.chiarani.otl.adapters.DeviceAdapter;
import it.chiarani.otl.adapters.RoomsAdapter;
import it.chiarani.otl.databinding.ActivityMainBinding;
import it.chiarani.otl.helper.Config;
import it.chiarani.otl.model.Device;
import it.chiarani.otl.model.HouseRoom;
import it.chiarani.otl.retrofit_model.RetrofitAuth;
import it.chiarani.otl.retrofit_model.RetrofitAuthRes;
import it.chiarani.otl.retrofit_model.RetrofitDevice;
import it.chiarani.otl.retrofit_model.RetrofitDevices;
import it.chiarani.otl.retrofit_model.RetrofitDiscover;
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
    RetrofitAPIClient retrofitAPIClient;
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

        retrofitAPIClient = retrofit.create(RetrofitAPIClient.class);

        Call<RetrofitDiscover> call = retrofitAPIClient.chiamataDiscover(Config.TMP_TOKEN);
        call.enqueue(new Callback<RetrofitDiscover>() {
            @Override
            public void onResponse(Call<RetrofitDiscover> call, Response<RetrofitDiscover> response) {

                response.body(); // have your all data
                RetrofitDevices mex =response.body().getMessage();
                RetrofitDevice x = mex.getDevices().get(0);

                if(x.getState() == 1)

                devices.add(new Device("h", x.getDevname(), x.getTopic(), "m", "n", "s", true, true));
                else
                    devices.add(new Device("h", x.getDevname(), x.getTopic(), "m", "n", "s", false, true));
                    deviceAdapter.notifyDataSetChanged();
             //   Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<RetrofitDiscover> call, Throwable t) {
                int x = 1;
            }
        });

        RetrofitAuth x = new RetrofitAuth();
        x.setClientUsername("op6-fabio");
        Call<RetrofitAuthRes> call1 = retrofitAPIClient.chiamataAuth(x);
        call1.enqueue(new Callback<RetrofitAuthRes>() {
            @Override
            public void onResponse(Call<RetrofitAuthRes> call, Response<RetrofitAuthRes> response) {


                response.body(); // have your all data
                String id =response.body().getToken();
                Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<RetrofitAuthRes> call, Throwable t) {
                int x = 1;
            }
        });

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
            Call<ServerRepository> call = retrofitAPIClient.chiamataOff(Config.TMP_TOKEN);
            call.enqueue(new Callback<ServerRepository>() {
                @Override
                public void onResponse(Call<ServerRepository> call, Response<ServerRepository> response) {


                    response.body(); // have your all data
                    String id =response.body().getMessage();
                    Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<ServerRepository> call, Throwable t) {
                    int x = 1;
                }
            });
        }
        if(status.equals("ON")) {
            Call<ServerRepository> call = retrofitAPIClient.chiamataOn(Config.TMP_TOKEN);
            call.enqueue(new Callback<ServerRepository>() {
                @Override
                public void onResponse(Call<ServerRepository> call, Response<ServerRepository> response) {
                    response.body(); // have your all data
                    String id =response.body().getMessage();
                    Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<ServerRepository> call, Throwable t) {
                    int x = 1;
                }
            });
        }

    }
}
