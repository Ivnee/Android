package com.geek.fragmentdz.WeatherJsonData;

public class WeatherDataController {
    private Weather []weather;
    private Main main;
    private Clouds clouds;
    private Sys sys;
    private String name;
    private int cod;

    public void setWeather(Weather[] weather) {
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
    }
}
