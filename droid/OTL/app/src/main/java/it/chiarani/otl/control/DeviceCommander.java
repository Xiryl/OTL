package it.chiarani.otl.control;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import it.chiarani.otl.ServerClient;
import it.chiarani.otl.ServerRepository;
import it.chiarani.otl.helper.APITypes;
import it.chiarani.otl.helper.Config;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeviceCommander {

    public DeviceCommander() {

    }

    public static boolean makeRequest(APITypes apiType) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.MQTT_BROKER_ADDR)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient())
                .build();

        ServerClient client = retrofit.create(ServerClient.class);

        switch (apiType) {
            case DEVICE_ON:

                Call<ServerRepository> call_on = client.chiamataOn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGllbnRfdXNlcm5hbWUiOiJvcDYtZmFiaW8iLCJpcCI6Ijo6ZmZmZjoxMjcuMC4wLjEiLCJpYXQiOjE1NDc5MTYxOTR9.y5kgvR2edN34CVlgpM2bYUGw4VowNrNOl9lqF2lmM9A");
                call_on.enqueue(new Callback<ServerRepository>() {
                    @Override
                    public void onResponse(Call<ServerRepository> call, Response<ServerRepository> response) {
                        int x = 1;
                    }

                    @Override
                    public void onFailure(Call<ServerRepository> call, Throwable t) {
                        int x = 1;
                    }
                });

                break;
            case DEVICE_OFF:
                Call<ServerRepository> call_off = client.chiamataOff("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGllbnRfdXNlcm5hbWUiOiJvcDYtZmFiaW8iLCJpcCI6Ijo6ZmZmZjoxMjcuMC4wLjEiLCJpYXQiOjE1NDc5MTYxOTR9.y5kgvR2edN34CVlgpM2bYUGw4VowNrNOl9lqF2lmM9A");
                call_off.enqueue(new Callback<ServerRepository>() {
                    @Override
                    public void onResponse(Call<ServerRepository> call, Response<ServerRepository> response) {
                        int x = 1;
                    }

                    @Override
                    public void onFailure(Call<ServerRepository> call, Throwable t) {
                        int x = 1;
                    }
                });

                break;
            case FULL_DISCOVERY: break;
            case ROOM_DISCOVERY: break;
            default : break;
        }
        return false;
    }

    /**********************************************************************************************/

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
