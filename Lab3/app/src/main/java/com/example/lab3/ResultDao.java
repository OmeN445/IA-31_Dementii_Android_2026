package com.example.lab3;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ResultDao {
    // Метод для додавання нового розрахунку
    @Insert
    void insert(CalculationResult result);

    // Метод для отримання всіх записів (спочатку новіші)
    @Query("SELECT * FROM results ORDER BY id DESC")
    List<CalculationResult> getAllResults();

    // Метод для очищення історії (опціонально для виконання лаби)
    @Query("DELETE FROM results")
    void deleteAll();
}