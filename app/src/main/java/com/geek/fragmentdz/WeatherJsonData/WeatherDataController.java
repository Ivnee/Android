package com.geek.fragmentdz.WeatherJsonData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherDataController {
    @SerializedName("weather")
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

 /*   public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Sys getSys() {
        return sys;
    }

    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }*/
}
