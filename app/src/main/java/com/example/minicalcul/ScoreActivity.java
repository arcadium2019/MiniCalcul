package com.example.minicalcul;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ScoreActivity extends AppCompatActivity {
    private int score;
    private EditText usernameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        usernameEditText = findViewById(R.id.usernameEditText);
        Button saveButton = findViewById(R.id.saveButton);

        score = getIntent().getIntExtra("score", 0);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveScore();
            }
        });
    }

    private void saveScore() {
        String username = usernameEditText.getText().toString();

        if (!username.isEmpty()) {
            DBHelper dbHelper = new DBHelper(this);
            ContentValues values = new ContentValues();
            values.put("pseudo", username);
            values.put("score", score);

            dbHelper.getWritableDatabase().insert("scores", null, values);
            dbHelper.close();

            Intent intent = new Intent(ScoreActivity.this, HighScoreActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Afficher un message d'erreur si le pseudo est vide
            Toast.makeText(this, R.string.empty_username_message, Toast.LENGTH_SHORT).show();
        }
    }
}
