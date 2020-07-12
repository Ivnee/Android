package com.geek.fragmentdz;

import android.graphics.drawable.Drawable;

public class RVWeatherContainer {
    String time;
    Drawable image;
    String temp;

    public RVWeatherContainer(String time, Drawable image, String temp) {
        this.time = time;
        this.image = image;
        this.temp = temp;
    }

}
