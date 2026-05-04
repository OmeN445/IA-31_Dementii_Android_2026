package com.example.lab4;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

public class MainActivity extends AppCompatActivity {

    private ExoPlayer player;
    private PlayerView playerView;
    private EditText etUrl;
    private TextView tvFileInfo;

    // Лаунчер для вибору файлу
    private final ActivityResultLauncher<String> filePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    playMedia(uri, "Локальний файл");
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerView = findViewById(R.id.playerView);
        etUrl = findViewById(R.id.etUrl);
        tvFileInfo = findViewById(R.id.tvFileInfo);
        Button btnPlayUrl = findViewById(R.id.btnPlayUrl);
        Button btnPickFile = findViewById(R.id.btnPickFile);

        // Ініціалізація плеєра
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        btnPlayUrl.setOnClickListener(v -> {
            String url = etUrl.getText().toString().trim();
            if (!url.isEmpty()) {
                try {
                    playMedia(Uri.parse(url), "Потік з інтернету");
                } catch (Exception e) {
                    Toast.makeText(this, "Помилка посилання", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Введіть URL", Toast.LENGTH_SHORT).show();
            }
        });

        btnPickFile.setOnClickListener(v -> filePickerLauncher.launch("video/* audio/*"));
    }

    private void playMedia(Uri uri, String description) {
        if (player != null) {
            MediaItem mediaItem = MediaItem.fromUri(uri);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();
            tvFileInfo.setText("Грає: " + description);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Краще зупиняти в onStop, щоб не витрачати ресурси
        if (player != null) {
            player.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}