package com.geek.fragmentdz;

import android.graphics.drawable.Drawable;

public class RVWeatherContainer {
    String Time;
    Drawable image;
    String temp;
    public RVWeatherContainer(String time, Drawable image, String temp) {
        Time = time;
        this.image = image;
        this.temp = temp;
    }

}
