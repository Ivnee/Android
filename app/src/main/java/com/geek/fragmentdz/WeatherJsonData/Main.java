package com.geek.fragmentdz.WeatherJsonData;

import com.google.gson.annotations.SerializedName;

public class Main {
    @SerializedName("temp") public int temp;
    @SerializedName("feels_like") public float feels_like;
    @SerializedName("temp_min") public float temp_min;
    @SerializedName("temp_max") public float temp_max;

/*
    public void setTemp(float temp) {
        this.temp = temp;
    }

    public void setFeels_like(float feels_like) {
        this.feels_like = feels_like;
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min;
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max;
    }

    public int getTemp() {
        return (int)temp;
    }

    public float getFeels_like() {
        return feels_like;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }
*/

}
