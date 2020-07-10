package com.geek.fragmentdz.citiesList;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(CitiesList.CITY_NAME)})
public class CitiesList {
    public final static String ID = "id";
    public final static String CITY_NAME = "cityName";
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @ColumnInfo(name = CITY_NAME)
    public String cityName;
}
