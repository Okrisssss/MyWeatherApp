package com.example.apple.myweatherapp;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherService {

    String BASE_URL = "http://openweathermap.org";


    @GET("/data/2.5/weather")

    Call<WeatherInfo> getMyWeatherGSON(@Query("q")String city, @Query("appid") String appid);

}
