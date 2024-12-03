package com.example.skycast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * WeatherData is a data model class used to map the JSON response from the OpenWeatherMap API.
 * It contains nested classes to represent different sections of the weather data, such as main,
 * wind, sys, and weather.
 */
public class WeatherData {

    @SerializedName("main")
    private MainData main;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("sys")
    private Sys sys;

    @SerializedName("weather")
    private List<Weather> weather;

    /**
     * Getter for the main weather data (temperature, humidity, etc.).
     *
     * @return The MainData object containing temperature, humidity, etc.
     */
    public MainData getMain() {
        return main;
    }

    /**
     * Getter for the weather details (main condition, description, etc.).
     *
     * @return A list of Weather objects containing detailed weather descriptions.
     */
    public List<Weather> getWeather() {
        return weather;
    }

    /**
     * Getter for wind data (speed).
     *
     * @return The Wind object containing wind speed.
     */
    public Wind getWind() {
        return wind;
    }

    /**
     * Getter for system data (sunrise, sunset).
     *
     * @return The Sys object containing sunrise and sunset times.
     */
    public Sys getSys() {
        return sys;
    }

    /**
     * MainData class represents the "main" JSON object, which contains data
     * like temperature, humidity, and feels-like temperature.
     */
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

        /**
         * Getter for the current temperature.
         *
         * @return The temperature value.
         */
        public double getTemp() {
            return temp;
        }

        /**
         * Getter for the minimum temperature.
         *
         * @return The minimum temperature value.
         */
        public double getTempMin() {
            return tempMin;
        }

        /**
         * Getter for the maximum temperature.
         *
         * @return The maximum temperature value.
         */
        public double getTempMax() {
            return tempMax;
        }

        /**
         * Getter for the "feels like" temperature.
         *
         * @return The "feels like" temperature value.
         */
        public double getFeelsLike() {
            return feelsLike;
        }

        /**
         * Getter for the humidity percentage.
         *
         * @return The humidity value as a percentage.
         */
        public long getHumidity() {
            return humidity;
        }
    }

    /**
     * Wind class represents the "wind" JSON object, which contains data about wind speed.
     */
    public static class Wind {
        @SerializedName("speed")
        private double speed;

        /**
         * Getter for the wind speed.
         *
         * @return The wind speed value.
         */
        public double getSpeed() {
            return speed;
        }
    }

    /**
     * Sys class represents the "sys" JSON object, which contains data like sunrise and sunset times.
     */
    public static class Sys {
        @SerializedName("sunset")
        private long sunset;
        @SerializedName("sunrise")
        private long sunrise;

        /**
         * Getter for the sunset time.
         *
         * @return The sunset time in UNIX timestamp format.
         */
        public long getSunset() {
            return sunset;
        }

        /**
         * Getter for the sunrise time.
         *
         * @return The sunrise time in UNIX timestamp format.
         */
        public long getSunrise() {
            return sunrise;
        }
    }

    /**
     * Weather class represents an element in the "weather" JSON array,
     * which contains data like the main weather condition and description.
     */
    public static class Weather {
        @SerializedName("main")
        private String main;
        @SerializedName("description")
        private String description;

        /**
         * Getter for the weather description.
         *
         * @return The weather description (e.g., "scattered clouds").
         */
        public String getDescription() {
            return description;
        }

        /**
         * Getter for the main weather condition.
         *
         * @return The main weather condition (e.g., "Clouds").
         */
        public String getMain() {
            return main;
        }
    }
}
