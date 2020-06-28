package com.geek.fragmentdz;

import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;

import com.geek.fragmentdz.WeatherJsonData.WeatherDataController;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WeatherData {
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s";
    private static final String KEY = "132fce3d69979894a33cf504082ed717";

    private OnLoadListener onLoadListener;

    public WeatherData(String city, final OnLoadListener onLoadListener) {
        try {
            final URL url = new URL(String.format(WEATHER_URL, city, KEY));
            final Handler handler = new Handler();
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run() {
                    HttpsURLConnection connection = null;
                    try {
                        connection = (HttpsURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setReadTimeout(10000);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String result;
                        while ((result = reader.readLine()) != null) {
                            sb.append(result).append("\n");
                        }
                        Gson gson = new Gson();
                        reader.close();
                        final WeatherDataController weatherDataController = gson.fromJson(sb.toString(), WeatherDataController.class);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                setWeatherData(weatherDataController, onLoadListener);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (connection != null) {
                            connection.disconnect();
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setWeatherData(WeatherDataController weatherDataController, OnLoadListener onLoadListener) {
        onLoadListener.onReadyData(weatherDataController);
    }

}
