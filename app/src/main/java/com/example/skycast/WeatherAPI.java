package com.example.skycast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * WeatherAPI is a Retrofit interface used to define HTTP requests for fetching
 * weather data from the OpenWeatherMap API.
 */
public interface WeatherAPI {
    // Example API call: https://api.openweathermap.org/data/2.5/weather?q={CityName}&appid={APIKEY}
    // Base URL: https://api.openweathermap.org/data/2.5/ (appended with "weather" and query parameters)

    /**
     * Makes a GET request to the OpenWeatherMap API to fetch weather data for a specified city.
     *
     * @param cityName The name of the city for which weather data is to be fetched.
     *                 This is appended as "?q={cityName}" to the URL.
     * @param apiKey   The API key for authenticating the request.
     *                 This is appended as "&appid={apiKey}" to the URL.
     * @return A Call object encapsulating the WeatherData response.
     */
    @GET("weather")
    Call<WeatherData> getData(
            @Query("q") String cityName, // Adds "?q=" with the city name to the URL
            @Query("appid") String apiKey // Adds "&appid=" with the API key to the URL
    );
}

/*
Example JSON Response:
{
  "coord": {
    "lon": -71.3162,
    "lat": 42.6334
  },
  "weather": [
    {
      "id": 802,
      "main": "Clouds",
      "description": "scattered clouds",
      "icon": "03n"
    }
  ],
  "base": "stations",
  "main": {
    "temp": 274.38,
    "feels_like": 270.3,
    "temp_min": 273.29,
    "temp_max": 275.66,
    "pressure": 1013,
    "humidity": 61,
    "sea_level": 1013,
    "grnd_level": 1006
  },
  "visibility": 10000,
  "wind": {
    "speed": 4.12,
    "deg": 270
  },
  "clouds": {
    "all": 40
  },
  "dt": 1733004014,
  "sys": {
    "type": 2,
    "id": 2009149,
    "country": "US",
    "sunrise": 1732967717,
    "sunset": 1733001202
  },
  "timezone": -18000,
  "id": 4942618,
  "name": "Lowell",
  "cod": 200
}
*/
