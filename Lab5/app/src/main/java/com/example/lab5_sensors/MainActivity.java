package com.example.lab5_sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private View bubble;
    private TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bubble = findViewById(R.id.bubble);
        tvData = findViewById(R.id.tvData);

        // Ініціалізуємо менеджер датчиків
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Реєструємо слухача з частотою UI (оптимально для плавності та батареї)
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Обов'язково відключаємо датчик при закритті додатку
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0]; // Нахил ліворуч/праворуч
            float y = event.values[1]; // Нахил вперед/назад

            // Оновлюємо текстову інформацію
            tvData.setText(String.format("X: %.2f | Y: %.2f", x, y));

            // Розрахунок руху бульбашки
            // Множник 15 визначає чутливість. Чим більший, тим швидше бігає кулька.
            float moveX = -x * 15;
            float moveY = y * 15;

            // Обмежуємо рух, щоб кулька не вилітала за межі (опціонально)
            bubble.setTranslationX(moveX);
            bubble.setTranslationY(moveY);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Не потребує реалізації для даного завдання
    }
}