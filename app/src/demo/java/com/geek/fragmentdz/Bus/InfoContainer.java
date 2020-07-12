package com.geek.fragmentdz.Bus;

import java.io.Serializable;

public class InfoContainer implements Serializable {
    public int currentPosition;
    public int temperature;
    public String cityName;
    public long sunset;
    public long sunrise;
    public int clouds;
    public int cod;
}
