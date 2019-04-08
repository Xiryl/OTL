package it.chiarani.otlsmartcontroller.views;

import android.app.Application;
import android.os.Bundle;

import com.bumptech.glide.Glide;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import it.chiarani.otlsmartcontroller.api.AuthBodyRetrofitModel;
import it.chiarani.otlsmartcontroller.api.AuthRetrofitModel;
import it.chiarani.otlsmartcontroller.api.RetrofitAPI;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.OTLRoomsEntity;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.User;
import it.chiarani.otlsmartcontroller.App;
import it.chiarani.otlsmartcontroller.R;
import it.chiarani.otlsmartcontroller.adapters.RoomsAdapter;
import it.chiarani.otlsmartcontroller.databinding.ActivityMainBinding;
import it.chiarani.otlsmartcontroller.viewmodels.UserProfileViewModel;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {


   ActivityMainBinding binding;
   UserProfileViewModel viewModel;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void setActivityBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutID());
    }

    protected UserProfileViewModel getViewModel() {
        if(viewModel == null) {
            Application application = getApplication();
            UserProfileViewModel.Factory factory = new UserProfileViewModel.Factory(application, ((App)getApplication()).getRepository());
            viewModel = ViewModelProviders.of(this, factory).get(UserProfileViewModel.class);
        }
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateUI();

        Retrofit.Builder builder = new Retrofit.Builder();

        builder.baseUrl("https://156.54.213.27/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient())
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
        });

    }

    private void updateUI() {
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

    private void bindRecyclerView(User userProfileEntity) {
        LinearLayoutManager linearLayoutManagerslot = new LinearLayoutManager(this);
        linearLayoutManagerslot.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.mainActivityRecyclerviewRooms.setLayoutManager(linearLayoutManagerslot);

        RoomsAdapter roomsAdapter = new RoomsAdapter(userProfileEntity);
        binding.mainActivityRecyclerviewRooms.setAdapter(roomsAdapter);
    }

    public static OkHttpClient getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            } };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient = okHttpClient.newBuilder()
                    .sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER).build();

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
