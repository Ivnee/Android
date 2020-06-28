package com.geek.fragmentdz.WeatherJsonData;

import com.google.gson.annotations.SerializedName;

public class Sys {
    @SerializedName("sunrise") public long sunrise;
    @SerializedName("sunset") public long sunset;

/*
    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }

*/

}
