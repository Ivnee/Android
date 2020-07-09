package com.geek.fragmentdz;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.geek.fragmentdz.citiesList.CitiesList;
import com.geek.fragmentdz.citiesList.CitiesListDao;

@Database(entities = {History.class, CitiesList.class}, version = 1)
public abstract class HistoryBuilderDB extends RoomDatabase {
    public abstract HistoryDao getHistoryDao();
    public abstract CitiesListDao getCitiesListDao();
}
