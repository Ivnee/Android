package com.geek.fragmentdz;

import android.os.Handler;

import com.geek.fragmentdz.Bus.InfoContainer;
import com.geek.fragmentdz.WeatherJsonData.WeatherDataController;

public class ParsingWeatherData implements OnLoadListener {
    OnSaveDataListener onSaveDataListener;
    String city;

    public ParsingWeatherData(OnSaveDataListener onSaveDataListener, String city) {
        this.onSaveDataListener = onSaveDataListener;
        this.city = city;
        WeatherData weatherData = new WeatherData(city, this);
    }

    @Override
    public void onReadyData(final WeatherDataController weatherDataController) {
        final InfoContainer infoContainer = new InfoContainer();
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                infoContainer.cod = weatherDataController.getCod();
                infoContainer.clouds = weatherDataController.getClouds().getAll();
                infoContainer.cityName = city;
                infoContainer.temperature = weatherDataController.getMain().getTemp();
                infoContainer.sunrise = weatherDataController.getSys().getSunrise();
                infoContainer.sunset = weatherDataController.getSys().getSunset();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setWeatherData(infoContainer, onSaveDataListener);
                    }
                });
            }
        }).start();
    }

    private void setWeatherData(InfoContainer infoContainer, OnSaveDataListener onSaveDataListener) {
        onSaveDataListener.onClickSaveData(infoContainer);
    }
}
