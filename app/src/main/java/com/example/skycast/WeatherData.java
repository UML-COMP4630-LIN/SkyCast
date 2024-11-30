package com.example.skycast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
This class
*/

public class WeatherData {
    // JSON Object of main
    @SerializedName("main")
    private MainData main;

    // JSON Object of wind
    @SerializedName("wind")
    private Wind wind;

    // JSON Object of sys
    @SerializedName("sys")
    private Sys sys;

    // JSON array of weather

    // Each JSON Object has their own class that holds the information needed from each key provided in the API
    @SerializedName("weather")
    private List<Weather> weather;
    public MainData getMain() {
        return main;
    }
    public List<Weather> getWeather() {
        return weather;
    }
    public Wind getWind() {
        return wind;
    }
    public Sys getSys() {
        return sys;
    }

    public static class MainData {
        @SerializedName("temp")
        private double temp;
        @SerializedName("temp_min")
        private double tempMin;
        @SerializedName("temp_max")
        private double tempMax;
        @SerializedName("feels_like")
        private double feelsLike;
        @SerializedName("humidity")
        private long humidity;

        public double getTemp() {
            return temp;
        }
        public double getTempMin(){
            return tempMin;
        }
        public double getTempMax(){
            return tempMax;
        }
        public double getFeelsLike(){
            return feelsLike;
        }
        public long getHumidity(){
            return humidity;
        }
    }
    public static class Wind {
        @SerializedName("speed")
        private double speed;
        public double getSpeed() {
            return speed;
        }
    }
    public static class Sys {
        @SerializedName("sunset")
        private long sunset;
        @SerializedName("sunrise")
        private long sunrise;
        public long getSunset() {
            return sunset;
        }
        public long getSunrise() {
            return sunrise;
        }
    }
    public static class Weather {
        @SerializedName("main")
        private String main;
        @SerializedName("description")
        private String description;
        public String getDescription() {
            return description;
        }
        public String getMain() {
            return main;
        }
    }
}
