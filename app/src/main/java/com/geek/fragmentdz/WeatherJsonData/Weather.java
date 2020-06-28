package com.geek.fragmentdz.WeatherJsonData;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("id") public int id;
    @SerializedName("main") public String main;
/*


    public void setId(int id) {
        this.id = id;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public int getId() {
        return id;
    }

    public String getMain() {
        return main;
    }
*/

}
