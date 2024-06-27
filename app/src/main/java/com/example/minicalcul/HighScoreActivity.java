package com.example.minicalcul;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HighScoreActivity extends AppCompatActivity {
    private TextView highScoreTextView;
    private TextView bestScoreTextView;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        highScoreTextView = findViewById(R.id.highScoreTextView);
        bestScoreTextView = findViewById(R.id.bestScoreTextView);
        dbHelper = new DBHelper(this);

        displayHighScores();
        displayBestScore();

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainActivity();
            }
        });
    }

    private void displayHighScores() {
        StringBuilder builder = new StringBuilder();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            // Query pour obtenir les meilleurs scores, triés par score décroissant
            Cursor cursor = db.query("scores", new String[]{"pseudo", "score"}, null, null, null, null, "score DESC");

            int rank = 1;
            while (cursor.moveToNext()) {
                String pseudo = cursor.getString(cursor.getColumnIndex("pseudo"));
                int score = cursor.getInt(cursor.getColumnIndex("score"));

                // Construction du texte à afficher pour chaque score
                builder.append(rank).append(". ").append(pseudo).append(": ").append(score).append("\n");

                rank++;
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e("HighScoreActivity", "Error fetching high scores: " + e.getMessage());
        }

        // Affichage des scores dans le TextView
        highScoreTextView.setText(builder.toString());
    }

    private void displayBestScore() {
        try {
            int bestScore = dbHelper.getBestScore();
            bestScoreTextView.setText(getString(R.string.best_score_label, bestScore));
        } catch (Exception e) {
            Log.e("HighScoreActivity", "Error fetching best score: " + e.getMessage());
        }
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(HighScoreActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Terminer l'activité en cours pour éviter de revenir ici en appuyant sur le bouton de retour
    }
}
