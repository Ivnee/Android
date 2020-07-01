package com.geek.fragmentdz;

import android.app.Application;

import androidx.room.Room;

public class AppHistoryDB extends Application {
    private static AppHistoryDB instance;
    private HistoryBuilderDB db;
    public static AppHistoryDB getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        db= Room.databaseBuilder(getApplicationContext(),HistoryBuilderDB.class,"builderDB").build();
    }

    public HistoryDao getHistoryBuilderDB(){
        return db.getHistoryDao();
    }
}
