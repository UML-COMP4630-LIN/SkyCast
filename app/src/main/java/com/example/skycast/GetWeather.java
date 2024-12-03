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
    private MutableLiveData<Boolean> isMetric = new MutableLiveData<>(true);
    private MutableLiveData<Double> currTemp = new MutableLiveData<>(0.);
    //need to makes these into MutuableLiveData Varaibles
    private MutableLiveData<Double> lowTemp =  new MutableLiveData<>(0.);
    private MutableLiveData<Double> highTemp =  new MutableLiveData<>(0.);
    private MutableLiveData<Double> feels_like =  new MutableLiveData<>(0.);
    private MutableLiveData<Long> humidity =  new MutableLiveData<>();
    private MutableLiveData<Long> sunset = new MutableLiveData<>();
    private MutableLiveData<Long> sunrise = new MutableLiveData<>();
    private MutableLiveData<String> main = new MutableLiveData<>("");
    private MutableLiveData<String> description = new MutableLiveData<>("");
    private MutableLiveData<Double> speed = new MutableLiveData<>(0.);
    public MutableLiveData<Double> getSpeed() {
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

    public MutableLiveData<Double> getCurrTemp() {
        return currTemp;
    }

    public MutableLiveData<Double> getLowTemp() {
        return lowTemp;
    }

    public MutableLiveData<Double> getHighTemp() {
        return highTemp;
    }

    public MutableLiveData<Double> getFeels_like() {
        return feels_like;
    }

    public MutableLiveData<Long> getHumidity() {
        return humidity;
    }

    public MutableLiveData<Boolean> getIsMetric() {
        return isMetric;
    }
    public void setIsMetric(Boolean val) {
        isMetric.setValue(val);
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
                    currTemp.setValue(weatherData.getMain().getTemp());
                    lowTemp.setValue(weatherData.getMain().getTempMin());
                    highTemp.setValue(weatherData.getMain().getTempMax());
                    humidity.setValue(weatherData.getMain().getHumidity());
                    speed.setValue(weatherData.getWind().getSpeed());
                    sunrise.setValue(weatherData.getSys().getSunrise());
                    sunset.setValue(weatherData.getSys().getSunset());
                    feels_like.setValue(weatherData.getMain().getFeelsLike());
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
