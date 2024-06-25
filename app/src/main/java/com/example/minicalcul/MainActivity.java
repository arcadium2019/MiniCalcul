package com.example.minicalcul;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playButton = findViewById(R.id.btn_play);
        Button highscoreButton = findViewById(R.id.btn_highscore);

        playButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        });

        highscoreButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HighscoreActivity.class);
            startActivity(intent);
        });
    }
}
