package com.example.lab3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Знаходимо елементи
        TextView tvHistory = findViewById(R.id.tvHistory);
        Button btnClear = findViewById(R.id.btnClear);
        Button btnBack = findViewById(R.id.btnBack);

        // 1. Завантажуємо дані
        loadHistory(tvHistory);

        // 2. Кнопка "Назад"
        btnBack.setOnClickListener(v -> {
            // Закриває цю Activity і повертає до попередньої (MainActivity)
            finish();
        });

        // 3. Кнопка "Очистити"
        btnClear.setOnClickListener(v -> {
            AppDatabase.getInstance(this).resultDao().deleteAll();
            tvHistory.setText("Сховище пусте");
            Toast.makeText(this, "Історію очищено", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadHistory(TextView tvHistory) {
        List<CalculationResult> results = AppDatabase.getInstance(this).resultDao().getAllResults();

        if (results.isEmpty()) {
            tvHistory.setText("Сховище пусте");
        } else {
            StringBuilder sb = new StringBuilder();
            for (CalculationResult item : results) {
                sb.append("• ").append(item.expression).append("\n\n");
            }
            tvHistory.setText(sb.toString());
        }
    }
}