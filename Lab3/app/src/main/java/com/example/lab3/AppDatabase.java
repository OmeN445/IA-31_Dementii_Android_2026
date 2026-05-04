package com.example.lab3;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Вказуємо, які таблиці (Entities) входять до бази та версію схеми
@Database(entities = {CalculationResult.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ResultDao resultDao();

    private static AppDatabase instance;


    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "lab3_database")
                    .allowMainThreadQueries() // Дозволяє запити в основному потоці (тільки для ЛР!)
                    .build();
        }
        return instance;
    }
}