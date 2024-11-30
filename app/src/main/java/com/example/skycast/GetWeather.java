package com.example.skycast;

import android.util.Log;


import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetWeather {
    private static final String apiKey = "ebb181acd73164fc4439615127a01f5f";
    private MutableLiveData<String> currTemp = new MutableLiveData<>("");
    private String lowTemp;
    private String highTemp;
    private String feels_like;
    private String humidity;
    private long sunset;
    private long sunrise;
    private String main;
    private String description;
    private String speed;
    public String getSpeed() {
        return speed;
    }
    public String getDescription() {
        return description;
    }
    public String getMain() {
        return main;
    }
    public long getSunset() {
        return sunset;
    }
    public long getSunrise() {
        return sunrise;
    }

    public MutableLiveData<String> getCurrTemp() {
        return currTemp;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public String getHighTemp() {
        return highTemp;
    }

    public String getFeels_like() {
        return feels_like;
    }

    public String getHumidity() {
        return humidity;
    }

    private String convertKelvinToFahrenheit(double kelvin) {
        return String.format("%.1f", (kelvin - 273.15) * 9 / 5 + 32);
    }
    public void getData(String city) {
        WeatherAPI myAPICall = RetrofitClient.getRetrofitInstance().create(WeatherAPI.class);
        Call<WeatherData> call = myAPICall.getData(city, apiKey);
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherData weatherData = response.body();
                    currTemp.setValue(convertKelvinToFahrenheit(weatherData.getMain().getTemp()) + " F°") ;
                    lowTemp = convertKelvinToFahrenheit(weatherData.getMain().getTempMin());
                    highTemp = convertKelvinToFahrenheit(weatherData.getMain().getTempMax());
                    humidity = String.valueOf(weatherData.getMain().getHumidity());
                    speed = String.valueOf(weatherData.getWind().getSpeed());
                    sunrise = weatherData.getSys().getSunrise();
                    sunset = weatherData.getSys().getSunset();
                    main = weatherData.getWeather().get(0).getMain();
                    description = weatherData.getWeather().get(0).getDescription();

                    Log.d("Weather Info", "Temp: " + currTemp);

                } else {
                    Log.d("Weather APP", "Response Code Value: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.e("Weather APP", "Error fetching Weather", t);
            }
        });
    }
}
