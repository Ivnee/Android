package com.geek.fragmentdz.WeatherJsonData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys {
    @SerializedName("sunrise") @Expose
    public long sunrise;
    @SerializedName("sunset") @Expose
    public long sunset;



}
