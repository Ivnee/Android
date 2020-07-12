package com.geek.fragmentdz.WeatherJsonData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("id")  @Expose
    public int id;
    @SerializedName("main") @Expose
    public String main;


}
