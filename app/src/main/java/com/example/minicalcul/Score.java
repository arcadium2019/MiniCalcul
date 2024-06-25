package com.example.minicalcul;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "score_table")
public class Score {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String username;
    public int score;

    public Score(String username, int score) {
        this.username = username;
        this.score = score;
    }

    @Override
    public String toString() {
        return username + ": " + score;
    }
}
