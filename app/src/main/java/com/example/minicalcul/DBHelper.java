package com.example.minicalcul;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "scores.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création de la table des scores
        db.execSQL("CREATE TABLE scores (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "pseudo TEXT," +
                "score INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Gestion de mise à jour de la base de données si nécessaire
    }

    public void insertScore(String pseudo, int score) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pseudo", pseudo);
        values.put("score", score);
        db.insert("scores", null, values);
        db.close();
    }

    public int getBestScore() {
        SQLiteDatabase db = getReadableDatabase();
        int bestScore = 0;
        Cursor cursor = null;

        try {
            // Query pour obtenir le score le plus élevé
            cursor = db.rawQuery("SELECT MAX(score) AS best_score FROM scores", null);

            if (cursor != null && cursor.moveToFirst()) {
                // Vérifier si la colonne best_score existe dans le curseur
                int columnIndex = cursor.getColumnIndex("best_score");
                if (columnIndex != -1) {
                    bestScore = cursor.getInt(columnIndex);
                } else {
                    // Log si la colonne best_score n'est pas trouvée
                    Log.e("DBHelper", "La colonne best_score n'a pas été trouvée dans le curseur");
                }
            }
        } catch (Exception e) {
            // Gérer toute exception ici, par exemple afficher un message dans Log
            Log.e("DBHelper", "Erreur lors de la récupération du meilleur score: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return bestScore;
    }
}
