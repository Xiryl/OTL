package it.chiarani.otl;

import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private static final String YOUR_HTTPS_URL = "\"https://156.54.213.27/\"";
    BottomAppBar bottomAppBar;
    Button accendi, spegni;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomAppBar = findViewById(R.id.bar);
        //setSupportActionBar(bottomAppBar);
        fab = findViewById(R.id.fav);

        bottomAppBar.replaceMenu(R.menu.bottom_menu);

          /* Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://156.54.213.27/")
                .addConverterFactory(GsonConverterFactory.create());*/



        // Retrofit retrofit = builder.build();



        accendi = findViewById(R.id.accendi);
        accendi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://156.54.213.27/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(getUnsafeOkHttpClient())
                        .build();

                ServerClient client = retrofit.create(ServerClient.class);

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
        });


        spegni = findViewById(R.id.spegni);
        spegni.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://156.54.213.27/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(getUnsafeOkHttpClient())
                        .build();

                ServerClient client = retrofit.create(ServerClient.class);

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
        });





        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomAppBar.getFabAlignmentMode() == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER) {
                    bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu));
                    //bottomAppBar.replaceMenu(R.menu.menu_secondary);
                } else {
                    bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_light));
                    //bottomAppBar.replaceMenu(R.menu.menu_primary);
                }
            }
        });

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
