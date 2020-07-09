package com.geek.fragmentdz.citiesList;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface CitiesListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCity(CitiesList citiesList);

    @Query("SELECT * FROM CitiesList")
    List<CitiesList> getFullCitiesLiist();

    @Query("DELETE FROM CitiesList WHERE id > 0")
    void deleteCitiesList();
}
