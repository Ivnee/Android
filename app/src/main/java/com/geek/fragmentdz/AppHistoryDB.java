package com.geek.fragmentdz;

import android.app.Application;

import androidx.room.Room;

import com.geek.fragmentdz.citiesList.CitiesListDao;

public class AppHistoryDB extends Application {
    private static AppHistoryDB instance;
    private HistoryBuilderDB db;
    private HistoryBuilderDB dbCities;
    public static AppHistoryDB getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        db= Room.databaseBuilder(getApplicationContext(),HistoryBuilderDB.class,"builderDB").fallbackToDestructiveMigration().build();
        dbCities = Room.databaseBuilder(getApplicationContext(),HistoryBuilderDB.class,"citiesDB").fallbackToDestructiveMigration().build();
    }

    public HistoryDao getHistoryBuilderDB(){
        return db.getHistoryDao();
    }
    public CitiesListDao getCitiesListDao() {
        return dbCities.getCitiesListDao();
    }
}
