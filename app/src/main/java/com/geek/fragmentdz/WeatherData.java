package com.geek.fragmentdz;

import com.geek.fragmentdz.WeatherJsonData.WeatherDataController;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherData {
    private static WeatherData weatherData = null;

    private RetrofitWeatherQuery API;

    public static WeatherData getInstance() {
        if (weatherData == null) {
            weatherData = new WeatherData();
        }
        return weatherData;
    }

    private WeatherData() {
        API = createAdapter();
    }
    public RetrofitWeatherQuery getAPI(){return API;}

    private RetrofitWeatherQuery createAdapter() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(RetrofitWeatherQuery.class);
    }

}
