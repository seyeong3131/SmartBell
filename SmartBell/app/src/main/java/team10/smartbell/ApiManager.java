package team10.smartbell;

import java.lang.ref.WeakReference;
import java.util.function.BiConsumer;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

class ApiManager {
    private static final String URL = "http://localhost/";


    private static WeakReference<ApiManager> reference;

    static ApiManager shared() {
        Object instance = (reference != null) ? reference.get() : null;

        if (instance == null)
            reference = new WeakReference<>(new ApiManager());
        return reference.get();
    }



    private ApiInterface api;

    private ApiManager() {
        api = getRetrofit().create(ApiInterface.class);
    }


    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .client(getClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient getClient() {
        final Headers.Builder headers = new Headers.Builder();
        headers.add("Content-Type", "application/json");

        Interceptor interceptor = chain -> {
            Request request = chain.request();

            if (request.body() == null)
                return chain.proceed(request);

            return chain.proceed(chain
                    .request()
                    .newBuilder()
                    .headers(headers.build())
                    .build()
            );
        };

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }


    @EverythingIsNonNull
    private <T> void request(Call<T> call, BiConsumer<Throwable, Response<T>> callback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                callback.accept(null, response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                callback.accept(t, null);
            }
        });
    }


    void order(Menu menu, BiConsumer<Throwable, Response<Boolean>> callback) {
        request(api.order(menu.getName()), callback);
    }
}
