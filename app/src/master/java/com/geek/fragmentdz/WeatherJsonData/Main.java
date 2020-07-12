package com.geek.fragmentdz.WeatherJsonData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {
    @SerializedName("temp") @Expose
    public float temp;
    @SerializedName("feels_like") @Expose
    public float feels_like;
    @SerializedName("temp_min") @Expose
    public float temp_min;
    @SerializedName("temp_max") @Expose
    public float temp_max;


}
