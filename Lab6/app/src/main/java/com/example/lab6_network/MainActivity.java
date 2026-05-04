package com.example.lab6_network; // ПЕРЕВІР, щоб назва пакету була твоя!

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);
        Button btnFetch = findViewById(R.id.btnFetch);

        btnFetch.setOnClickListener(v -> {
            tvResult.setText("Завантаження...");
            fetchData();
        });
    }

    private void fetchData() {
        // Створюємо новий потік для роботи з мережею
        new Thread(() -> {
            try {
                // 1. Формуємо URL до API Нацбанку
                URL url = new URL("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=USD&json");

                // 2. Відкриваємо з'єднання
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                // 3. Читаємо відповідь
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // 4. Парсимо JSON (це структура даних, яку прислав банк)
                // Банк присилає масив [ {...} ], тому беремо JSONArray
                JSONArray jsonArray = new JSONArray(response.toString());
                JSONObject usdObject = jsonArray.getJSONObject(0);

                String currencyName = usdObject.getString("txt");
                double rate = usdObject.getDouble("rate");
                String date = usdObject.getString("exchangedate");

                // 5. Повертаємось у головний потік, щоб оновити екран
                runOnUiThread(() -> {
                    tvResult.setText(String.format("%s\n\nКурс: %.2f грн\nДата: %s", currencyName, rate, date));
                });

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> tvResult.setText("Помилка: " + e.getMessage()));
            }
        }).start();
    }
}