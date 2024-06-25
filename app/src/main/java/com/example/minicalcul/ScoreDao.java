package com.example.minicalcul;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ScoreDao {
    @Insert
    void insert(Score score);

    @Query("SELECT * FROM score_table ORDER BY score DESC")
    List<Score> getAllScores();
}
