package com.example.minicalcul;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SaveScoreActivity extends AppCompatActivity {

    private EditText etUsername;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_score);

        etUsername = findViewById(R.id.et_username);
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(v -> saveScore());
    }

    private void saveScore() {
        String username = etUsername.getText().toString().trim();
        if (!username.isEmpty()) {
            int score = getIntent().getIntExtra("score", 0);

            // Enregistrer le score avec le pseudo dans la base de données ou le stockage local
            // Par exemple, vous pourriez utiliser SharedPreferences ou SQLite pour la sauvegarde

            // Exemple d'utilisation de SharedPreferences pour sauvegarder le score
            SharedPreferences preferences = getSharedPreferences("scores", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(username, score);
            editor.apply();

            Toast.makeText(this, "Score enregistré avec succès!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Veuillez entrer un pseudo valide.", Toast.LENGTH_SHORT).show();
        }
    }
}
