package com.geek.fragmentdz.WeatherJsonData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherDataController {
    @SerializedName("weather")@Expose
    public Weather []weather;
    @SerializedName("main") @Expose
    public Main main;
    @SerializedName("clouds") @Expose
    public Clouds clouds;
    @SerializedName("sys") @Expose
    public Sys sys;
    @SerializedName("name") @Expose
    public String name;
    @SerializedName("cod") @Expose
    public int cod;


}
