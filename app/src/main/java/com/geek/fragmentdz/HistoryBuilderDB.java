package com.geek.fragmentdz;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {History.class}, version = 1)
public abstract class HistoryBuilderDB extends RoomDatabase {
    public abstract HistoryDao getHistoryDao();
}
