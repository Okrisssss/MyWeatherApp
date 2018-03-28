package com.example.apple.myweatherapp.networking;

import android.content.Context;

import com.example.apple.myweatherapp.model.WeatherInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aiachimov on 3/25/18.
 */

public class NetworkingManager {

  private static String BASE_URL = "http://openweathermap.org/";
  private static String WEATHER_API_KEY = "b6907d289e10d714a6e88b30761fae22";

  private WeatherCallback weatherCallback;
  private Retrofit retrofitInstance;
  private WeatherService weatherService;
  private Context context;

  public NetworkingManager(Context context) {
    this.context = context;
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    retrofitInstance = builder.build();
    initWeatherRetrofitService();
  }

  public void setWeatherCallback(WeatherCallback weatherCallback) {
    this.weatherCallback = weatherCallback;
  }

  public Retrofit getRetrofitInstance() {
    return retrofitInstance;
  }

  public void setRetrofitInstance(Retrofit retrofitInstance) {
    this.retrofitInstance = retrofitInstance;
  }

  private void initWeatherRetrofitService() {
    weatherService = retrofitInstance.create(WeatherService.class);
  }

  public void getCurrentTemperatureForCity(String city) {
    final String[] weatherIcon = new String[1];
    final String[] temperature = new String[1];
    Call<WeatherInfo> call = weatherService.getWeatherByCity(city, WEATHER_API_KEY);

    call.enqueue(new Callback<WeatherInfo>() {
      @Override
      public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
        WeatherInfo weatherInfo = response.body();
        weatherIcon[0] = weatherInfo.getWeather().get(0).getIcon();
        temperature[0] = String.valueOf(weatherInfo.getMain().getTemp());
        weatherCallback.onWeatherLoaded(temperature[0], weatherIcon[0]);
      }

      @Override
      public void onFailure(Call<WeatherInfo> call, Throwable t) {
        weatherCallback.onFailedWeatherLoading(t);
      }
    });
  }


  public interface WeatherCallback {
    void onWeatherLoaded(String temperature, String weatherIcon);

    void onFailedWeatherLoading(Throwable throwable);
  }
}
