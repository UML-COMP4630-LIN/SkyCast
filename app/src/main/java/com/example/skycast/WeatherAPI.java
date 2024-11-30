package com.example.skycast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {
    //https://api.openweathermap.org/data/2.5/weather?q=  Lowell  &appid=ebb181acd73164fc4439615127a01f5f
    @GET("weather")//maybe add a ? after weather
    Call<WeatherData> getData(
            @Query("q") String cityName, // this is the q
            @Query("appid") String apiKey
    );
}

/*
Example:
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
