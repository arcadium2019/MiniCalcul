package com.example.minicalcul;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private int score = 0;
    private int lives = 3;
    private int correctAnswers = 0; // Compteur de bonnes réponses
    private TextView scoreTextView;
    private TextView livesTextView;
    private TextView questionTextView;
    private EditText answerEditText;
    private DBHelper dbHelper;
    private Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        scoreTextView = findViewById(R.id.scoreTextView);
        livesTextView = findViewById(R.id.livesTextView);
        questionTextView = findViewById(R.id.questionTextView);
        answerEditText = findViewById(R.id.answerEditText);

        dbHelper = new DBHelper(this); // Initialisation du dbHelper
        rand = new Random();

        updateScoreAndLives();

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    private void updateScoreAndLives() {
        scoreTextView.setText(getString(R.string.score_label, score));
        livesTextView.setText(getString(R.string.lives_label, lives));
        generateQuestion();
    }

    private void generateQuestion() {
        int a, b;
        String operatorString;
        int result;

        // Augmentation de la difficulté en fonction du nombre de bonnes réponses
        if (correctAnswers < 5) {
            // Niveau facile : nombres entre 1 et 20 inclus
            a = rand.nextInt(20) + 1;
            b = rand.nextInt(20) + 1;
        } else if (correctAnswers < 10) {
            // Niveau moyen : nombres entre 1 et 50 inclus
            a = rand.nextInt(50) + 1;
            b = rand.nextInt(50) + 1;
        } else {
            // Niveau difficile : nombres entre 1 et 100 inclus
            a = rand.nextInt(100) + 1;
            b = rand.nextInt(100) + 1;
        }

        int operator = rand.nextInt(2); // 0: +, 1: *

        if (operator == 0) {
            operatorString = " + ";
            result = a + b;
        } else {
            operatorString = " * ";
            result = a * b;
        }

        questionTextView.setText(getString(R.string.question_label, a, operatorString, b));
        questionTextView.setTag(result); // Tagging the correct answer with the TextView
    }

    private void checkAnswer() {
        String answerStr = answerEditText.getText().toString().trim();
        if (answerStr.isEmpty()) {
            Toast.makeText(GameActivity.this, R.string.empty_answer_message, Toast.LENGTH_SHORT).show();
            return;
        }

        int answer;
        try {
            answer = Integer.parseInt(answerStr);
        } catch (NumberFormatException e) {
            Toast.makeText(GameActivity.this, R.string.empty_answer_message, Toast.LENGTH_SHORT).show();
            return;
        }

        int correctAnswer = (int) questionTextView.getTag();

        if (answer == correctAnswer) {
            score++;
            correctAnswers++;
            Toast.makeText(GameActivity.this, R.string.correct_answer_message, Toast.LENGTH_SHORT).show();
        } else {
            lives--;
            correctAnswers = 0; // Réinitialiser le compteur en cas de mauvaise réponse
            Toast.makeText(GameActivity.this, R.string.wrong_answer_message, Toast.LENGTH_SHORT).show();
            if (lives == 0) {
                gameOver();
                return;
            }
        }

        updateScoreAndLives();

        // Vider le champ de texte answerEditText
        answerEditText.setText("");
    }

    private void gameOver() {
        // Redirection vers l'activité de saisie du pseudo
        Intent intent = new Intent(GameActivity.this, ScoreActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }
}
