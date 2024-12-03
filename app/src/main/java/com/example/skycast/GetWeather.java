package com.example.skycast;

import android.util.Log;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * GetWeather is a ViewModel class responsible for fetching weather data from an API
 * and storing the weather details as MutableLiveData for UI updates.
 */

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

    /**
     * Getter for wind speed.
     *
     * @return A MutableLiveData object containing the wind speed.
     */
    public MutableLiveData<Double> getSpeed() {
        return speed;
    }

    /**
     * Getter for weather description.
     *
     * @return A MutableLiveData object containing the weather description.
     */
    public MutableLiveData<String> getDescription() {
        return description;
    }

    /**
     * Getter for weather main condition.
     *
     * @return A MutableLiveData object containing the main weather condition.
     */
    public MutableLiveData<String> getMain() {
        return main;
    }

    /**
     * Getter for sunset time.
     *
     * @return A MutableLiveData object containing the sunset time in UNIX timestamp.
     */
    public MutableLiveData<Long> getSunset() {
        return sunset;
    }

    /**
     * Getter for sunrise time.
     *
     * @return A MutableLiveData object containing the sunrise time in UNIX timestamp.
     */
    public MutableLiveData<Long> getSunrise() {
        return sunrise;
    }

    /**
     * Getter for the current temperature.
     *
     * @return A MutableLiveData object containing the current temperature.
     */
    public MutableLiveData<Double> getCurrTemp() {
        return currTemp;
    }

    /**
     * Getter for the lowest temperature.
     *
     * @return A MutableLiveData object containing the lowest temperature.
     */
    public MutableLiveData<Double> getLowTemp() {
        return lowTemp;
    }

    /**
     * Getter for the highest temperature.
     *
     * @return A MutableLiveData object containing the highest temperature.
     */
    public MutableLiveData<Double> getHighTemp() {
        return highTemp;
    }

    /**
     * Getter for the "feels like" temperature.
     *
     * @return A MutableLiveData object containing the "feels like" temperature.
     */
    public MutableLiveData<Double> getFeels_like() {
        return feels_like;
    }

    /**
     * Getter for humidity percentage.
     *
     * @return A MutableLiveData object containing the humidity percentage.
     */
    public MutableLiveData<Long> getHumidity() {
        return humidity;
    }

    /**
     * Getter for metric system preference.
     *
     * @return A MutableLiveData object indicating whether metric units are used.
     */
    public MutableLiveData<Boolean> getIsMetric() {
        return isMetric;
    }

    /**
     * Setter for metric system preference.
     *
     * @param val A boolean value indicating whether to use metric units.
     */
    public void setIsMetric(Boolean val) {
        isMetric.setValue(val);
    }

    /**
     * Fetches weather data for a given city using an API call.
     *
     * @param city The name of the city for which weather data is to be fetched.
     */
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
