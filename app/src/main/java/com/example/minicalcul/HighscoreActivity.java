package com.example.minicalcul;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class HighscoreActivity extends AppCompatActivity {
    private TextView tvHighscore;
    private ListView lvScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        tvHighscore = findViewById(R.id.tv_highscore);
        lvScores = findViewById(R.id.lv_scores);

        loadHighScores();
    }

    private void loadHighScores() {
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        AsyncTask.execute(() -> {
            List<Score> scores = db.scoreDao().getAllScores();
            runOnUiThread(() -> {
                if (!scores.isEmpty()) {
                    Score highScore = scores.get(0);
                    tvHighscore.setText(getString(R.string.highscore_display, highScore.username, highScore.score));
                    ArrayAdapter<Score> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scores);
                    lvScores.setAdapter(adapter);
                } else {
                    tvHighscore.setText(R.string.no_highscores);
                }
            });
        });
    }
}
