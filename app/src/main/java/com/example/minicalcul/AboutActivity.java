package com.example.minicalcul;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView aboutTextView = findViewById(R.id.aboutTextView);
        aboutTextView.setText("Développé par [Nom1] et [Nom2]\nFonctionnalités supplémentaires: ...");
    }
}
