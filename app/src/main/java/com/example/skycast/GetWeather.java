package com.example.skycast;

import android.util.Log;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetWeather extends ViewModel {
    private static final String apiKey = "ebb181acd73164fc4439615127a01f5f";
    private MutableLiveData<String> currTemp = new MutableLiveData<>("");
    //need to makes these into MutuableLiveData Varaibles
    private MutableLiveData<String> lowTemp =  new MutableLiveData<>("");
    private MutableLiveData<String> highTemp =  new MutableLiveData<>("");
    private MutableLiveData<String> feels_like =  new MutableLiveData<>("");
    private MutableLiveData<String> humidity =  new MutableLiveData<>("");
    private MutableLiveData<Long> sunset = new MutableLiveData<>();
    private MutableLiveData<Long> sunrise = new MutableLiveData<>();
    private MutableLiveData<String> main = new MutableLiveData<>("");
    private MutableLiveData<String> description = new MutableLiveData<>("");
    private MutableLiveData<String> speed = new MutableLiveData<>("");
    public MutableLiveData<String> getSpeed() {
        return speed;
    }
    public MutableLiveData<String> getDescription() {
        return description;
    }
    public MutableLiveData<String> getMain() {
        return main;
    }
    public MutableLiveData<Long> getSunset() {
        return sunset;
    }
    public MutableLiveData<Long> getSunrise() {
        return sunrise;
    }

    public MutableLiveData<String> getCurrTemp() {
        return currTemp;
    }

    public MutableLiveData<String> getLowTemp() {
        return lowTemp;
    }

    public MutableLiveData<String> getHighTemp() {
        return highTemp;
    }

    public MutableLiveData<String> getFeels_like() {
        return feels_like;
    }

    public MutableLiveData<String> getHumidity() {
        return humidity;
    }

    private String convertKelvinToFahrenheit(double kelvin) {
        return String.format("%.1f", (kelvin - 273.15) * 9 / 5 + 32);
    }
    public void getData(String city) {
        // Set up the API call and try to connect
        Log.d("Get Data", city);
        WeatherAPI myAPICall = RetrofitClient.getRetrofitInstance().create(WeatherAPI.class);
        Call<WeatherData> call = myAPICall.getData(city, apiKey);
        call.enqueue(new Callback<WeatherData>() {
            // Try to connect to the API URL check if it worked other wised let us know why it didn't
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherData weatherData = response.body();
                    //place all the JSON values from Weather Data into this classes Variables
                    currTemp.setValue(convertKelvinToFahrenheit(weatherData.getMain().getTemp()) + " F°") ;
                    lowTemp.setValue(convertKelvinToFahrenheit(weatherData.getMain().getTempMin()) + " F°");
                    highTemp.setValue(convertKelvinToFahrenheit(weatherData.getMain().getTempMax()) +  " F°");
                    humidity.setValue(String.valueOf(weatherData.getMain().getHumidity()));
                    speed.setValue(String.valueOf(weatherData.getWind().getSpeed()));
                    sunrise.setValue(weatherData.getSys().getSunrise());
                    sunset.setValue(weatherData.getSys().getSunset());
                    feels_like.setValue(String.valueOf(weatherData.getMain().getFeelsLike()));
                    main.setValue(weatherData.getWeather().get(0).getMain());
                    description.setValue(weatherData.getWeather().get(0).getDescription());

                    Log.d("Weather Info", "Temp: " + currTemp);

                } else {
                    Log.d("Weather APP", "Response Code Value: " + response.code());
                }
            }
            // if API Fails then send an error log message
            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.e("Weather APP", "Error fetching Weather", t);
            }
        });
    }
}
