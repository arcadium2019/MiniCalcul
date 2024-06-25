package com.example.minicalcul;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private TextView tvScore;
    private TextView tvLives;
    private TextView tvCalculation;
    private EditText etAnswer;
    private Button btnSubmit;

    private int score = 0;
    private int lives = 3;
    private int correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvScore = findViewById(R.id.tv_score);
        tvLives = findViewById(R.id.tv_lives);
        tvCalculation = findViewById(R.id.tv_calculation);
        etAnswer = findViewById(R.id.et_answer);
        btnSubmit = findViewById(R.id.btn_submit);

        updateScoreAndLives();
        generateNewCalculation();

        btnSubmit.setOnClickListener(v -> checkAnswer());
    }

    private void updateScoreAndLives() {
        tvScore.setText(getString(R.string.score, score));
        tvLives.setText(getString(R.string.lives, lives));
    }

    private void generateNewCalculation() {
        Random random = new Random();
        int number1 = random.nextInt(10) + 1;
        int number2 = random.nextInt(10) + 1;
        correctAnswer = number1 + number2;

        tvCalculation.setText(getString(R.string.calculation, number1, number2));
    }

    private void checkAnswer() {
        String answerText = etAnswer.getText().toString();
        if (!answerText.isEmpty()) {
            int answer = Integer.parseInt(answerText);
            if (answer == correctAnswer) {
                score++;
            } else {
                lives--;
            }

            updateScoreAndLives();
            etAnswer.setText("");

            if (lives > 0) {
                generateNewCalculation();
            } else {
                endGame();
            }
        }
    }

    private void endGame() {
        Intent intent = new Intent(GameActivity.this, SaveScoreActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }

}
