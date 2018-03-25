package com.example.apple.myweatherapp.networking;

import com.example.apple.myweatherapp.model.WeatherInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherService {

  @GET("/data/2.5/weather")
  Call<WeatherInfo> getWeatherByCity(@Query("q") String city, @Query("appid") String appid);
}
