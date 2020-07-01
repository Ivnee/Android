package com.geek.fragmentdz;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.geek.fragmentdz.History;

import java.util.List;

@Dao
public interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertHistory(History history);

    @Query("SELECT * FROM history")
    List<History> getFullHistory();

    @Query("DELETE FROM history WHERE id >= 0")
    void deleteHistory();

}
