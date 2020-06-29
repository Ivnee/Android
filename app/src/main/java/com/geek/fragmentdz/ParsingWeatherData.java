package com.geek.fragmentdz;

import android.os.Handler;

import androidx.annotation.NonNull;

import com.geek.fragmentdz.Bus.InfoContainer;
import com.geek.fragmentdz.WeatherJsonData.WeatherDataController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParsingWeatherData {/*
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s";*/
    private static final String METRIC_SYSTEM = "metric";
    private static final String KEY = "132fce3d69979894a33cf504082ed717";


    public ParsingWeatherData(OnSaveDataListener onSaveDataListener, String city) {
        parsData(city,onSaveDataListener);
    }


    private void parsData(String city , OnSaveDataListener onSaveDataListener) {
        WeatherData.getInstance().getAPI().loadWeather(city,KEY,METRIC_SYSTEM).enqueue(new Callback<WeatherDataController>() {
            @Override
            public void onResponse(@NonNull Call<WeatherDataController> call, @NonNull Response<WeatherDataController> response) {
                if (response.body() != null && response.isSuccessful()) {
                    final InfoContainer infoContainer = new InfoContainer();
                    final WeatherDataController model = response.body();
                    final Handler handler = new Handler();
                    new Thread(() -> {
                        infoContainer.cod = model.cod;
                        infoContainer.clouds = model.clouds.all;
                        infoContainer.cityName = city;
                        infoContainer.temperature = model.main.temp;
                        infoContainer.sunrise = model.sys.sunrise;
                        infoContainer.sunset = model.sys.sunset;
                        handler.post(() -> setWeatherData(infoContainer, onSaveDataListener));
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<WeatherDataController> call, Throwable t) {

            }
        });
    }

    private void setWeatherData(InfoContainer infoContainer, OnSaveDataListener onSaveDataListener) {
        onSaveDataListener.onClickSaveData(infoContainer);
    }
}
