package com.geek.fragmentdz;

import android.content.Context;
import android.content.Intent;
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
    private static final String CONNECT_ACTION = "com.geek.fragmentdz.connection";
    Context context;


    public ParsingWeatherData(OnSaveDataListener onSaveDataListener, String city, Context context) {
        parsData(city, onSaveDataListener);
        this.context = context;
    }


    private void parsData(String city, OnSaveDataListener onSaveDataListener) {
        WeatherData.getInstance().getAPI().loadWeather(city, KEY, METRIC_SYSTEM).enqueue(new Callback<WeatherDataController>() {
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
                        infoContainer.temperature = (int) model.main.temp;
                        infoContainer.sunrise = model.sys.sunrise;
                        infoContainer.sunset = model.sys.sunset;
                        handler.post(() -> setWeatherData(infoContainer, onSaveDataListener));
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<WeatherDataController> call, Throwable t) {
                t.printStackTrace();
                Intent intent = new Intent();
                intent.setAction(CONNECT_ACTION);
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                context.sendBroadcast(intent);
            }
        });
    }

    private void setWeatherData(InfoContainer infoContainer, OnSaveDataListener onSaveDataListener) {
        onSaveDataListener.onClickSaveData(infoContainer);
    }
}
