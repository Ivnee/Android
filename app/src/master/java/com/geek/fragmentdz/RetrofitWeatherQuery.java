package com.geek.fragmentdz;

import com.geek.fragmentdz.WeatherJsonData.WeatherDataController;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitWeatherQuery {
    @GET("data/2.5/weather")
    Call<WeatherDataController> loadWeather(@Query("q") String city,
                                              @Query("appid") String keyApi,
                                              @Query("units") String units);
}
