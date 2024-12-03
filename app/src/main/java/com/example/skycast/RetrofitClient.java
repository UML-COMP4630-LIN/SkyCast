package com.example.skycast;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RetrofitClient is a singleton class responsible for creating and managing
 * a Retrofit instance for making API calls to the OpenWeatherMap API.
 */
public class RetrofitClient {
    private static final String baseURL = "https://api.openweathermap.org/data/2.5/";
    private static Retrofit retrofit = null;

    /**
     * Returns a singleton Retrofit instance for making HTTP requests.
     * Configures the client with logging and a GSON converter.
     *
     * @return A Retrofit instance configured for the base URL and client settings.
     */
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
