package com.geek.fragmentdz;

import com.geek.fragmentdz.WeatherJsonData.WeatherDataController;

public interface OnLoadListener {
    void onReadyData(WeatherDataController weatherDataController);
}
