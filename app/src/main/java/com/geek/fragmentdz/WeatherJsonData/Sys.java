package com.geek.fragmentdz.WeatherJsonData;

public class Sys {
    private long sunrise;
    private long sunset;

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }


}
