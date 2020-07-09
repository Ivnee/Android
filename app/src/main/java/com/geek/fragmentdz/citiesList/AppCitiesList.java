/*
package com.geek.fragmentdz.citiesList;

import android.app.Application;

import androidx.room.Room;

public class AppCitiesList extends Application {
    public static AppCitiesList instance;
    private CitiesListBuilder db;
    public static AppCitiesList getInstance(){return instance;}

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        db = Room.databaseBuilder(getApplicationContext(),CitiesListBuilder.class,"citiesListDB").build();
    }
    public CitiesListDao getCitiesListBuilder(){
        return db.getCitiesListDao();
    }
}
*/
