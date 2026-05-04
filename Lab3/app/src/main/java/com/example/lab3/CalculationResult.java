package com.example.lab3;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Клас-сутність, що визначає структуру таблиці "results".
 * Кожне поле класу автоматично стає стовпцем у базі даних.
 */
@Entity(tableName = "results")
public class CalculationResult {

    // Первинний ключ з автогенерацією (автоінкремент)
    @PrimaryKey(autoGenerate = true)
    public int id;

    // Поле для збереження математичного виразу та результату
    public String expression;
}