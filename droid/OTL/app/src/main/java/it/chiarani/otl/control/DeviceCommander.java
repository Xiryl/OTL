package it.chiarani.otl.control;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import it.chiarani.otl.model.Device;
import it.chiarani.otl.retrofit_model.GET_RetrofitControlResponseModel;
import it.chiarani.otl.helper.APITypes;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceCommander {

    public DeviceCommander() {

    }

    public static Call<GET_RetrofitControlResponseModel> sendCommand(APITypes apiType, RetrofitAPI client, String authToken, Device device) {

        switch (apiType) {
            case DEVICE_ON:
                Call<GET_RetrofitControlResponseModel> call_on = client.APIControlDevice(authToken, device.getTopic(), device.getName(), "ON");
                return call_on;
                /*
                int x = 0;
                call_on.enqueue(new Callback<GET_RetrofitControlResponseModel>() {
                    @Override
                    public void onResponse(Call<GET_RetrofitControlResponseModel> call, Response<GET_RetrofitControlResponseModel> response) {
                        x = 1;
                        return;
                    }

                    @Override
                    public void onFailure(Call<GET_RetrofitControlResponseModel> call, Throwable t) {
                        int x = 1;
                    }
                });*/

            case FULL_DISCOVERY: break;
            case ROOM_DISCOVERY: break;
            default : return  null;
        }

        return null;
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
