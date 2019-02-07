package it.chiarani.otl.views;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import com.google.android.material.bottomappbar.BottomAppBar;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import it.chiarani.otl.R;
import it.chiarani.otl.ServerClient;
import it.chiarani.otl.ServerRepository;
import it.chiarani.otl.adapters.DeviceAdapter;
import it.chiarani.otl.adapters.RoomsAdapter;
import it.chiarani.otl.databinding.ActivityMainBinding;
import it.chiarani.otl.helper.Config;
import it.chiarani.otl.model.Device;
import it.chiarani.otl.model.HouseRoom;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static it.chiarani.otl.control.DeviceCommander.getUnsafeOkHttpClient;


public class MainActivity extends BaseActivity implements DeviceAdapter.ClickListener {

    private final String            TAG = this.getClass().getSimpleName();
    private ActivityMainBinding     binding;


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
       // fabClickListener();


        List<Device> devices = new ArrayList<>();
        devices.add(new Device("h", "Luce Salotto", "salotto/lampadina", "m", "n", "s", true, true));
        devices.add(new Device("h", "Luce Principale", "camera-letto/fabio", "m", "n", "s", true, true));
        devices.add(new Device("h", "Luce Letto", "camera-letto/fabio", "m", "n", "s", true, true));

        List<HouseRoom> rooms = new ArrayList<>();
        rooms.add(new HouseRoom("h", "Salotto", "Salotto", "m", "n", "s", true, true));
        rooms.add(new HouseRoom("h", "Cucina", "Cucina", "m", "n", "s", true, true));

        binding.mainactivityRvDevices.setHasFixedSize(true);
        binding.mainactivityRvRooms.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManagerslot = new LinearLayoutManager(this);
        linearLayoutManagerslot.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.mainactivityRvDevices.setLayoutManager(linearLayoutManagerslot);

        LinearLayoutManager linearLayoutManagerslot_rooms = new LinearLayoutManager(this);
        linearLayoutManagerslot_rooms.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.mainactivityRvRooms.setLayoutManager(linearLayoutManagerslot_rooms);

        DeviceAdapter deviceAdapter = new DeviceAdapter(getApplicationContext(),this, devices);
        binding.mainactivityRvDevices.setAdapter(deviceAdapter);

        RoomsAdapter roomsAdapter = new RoomsAdapter(getApplicationContext(),null, rooms);
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.MQTT_BROKER_ADDR)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient())
                .build();

        ServerClient client = retrofit.create(ServerClient.class);

        if(status.equals("OFF")) {
            Call<ServerRepository> call = client.chiamataOff("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGllbnRfdXNlcm5hbWUiOiJvcDYtZmFiaW8iLCJpcCI6Ijo6ZmZmZjoxMjcuMC4wLjEiLCJpYXQiOjE1NDc5MTYxOTR9.y5kgvR2edN34CVlgpM2bYUGw4VowNrNOl9lqF2lmM9A");
            call.enqueue(new Callback<ServerRepository>() {
                @Override
                public void onResponse(Call<ServerRepository> call, Response<ServerRepository> response) {
                    int x = 1;
                }

                @Override
                public void onFailure(Call<ServerRepository> call, Throwable t) {
                    int x = 1;
                }
            });
        }
        if(status.equals("ON")) {
            Call<ServerRepository> call = client.chiamataOn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGllbnRfdXNlcm5hbWUiOiJvcDYtZmFiaW8iLCJpcCI6Ijo6ZmZmZjoxMjcuMC4wLjEiLCJpYXQiOjE1NDc5MTYxOTR9.y5kgvR2edN34CVlgpM2bYUGw4VowNrNOl9lqF2lmM9A");
            call.enqueue(new Callback<ServerRepository>() {
                @Override
                public void onResponse(Call<ServerRepository> call, Response<ServerRepository> response) {
                    int x = 1;
                }

                @Override
                public void onFailure(Call<ServerRepository> call, Throwable t) {
                    int x = 1;
                }
            });
        }

    }
}
