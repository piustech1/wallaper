package com.example.wallpaperapp;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.ComponentActivity;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PreviewActivity extends ComponentActivity {

    private ImageView ivPreview;
    private Button btnSetWallpaper;
    private String imageUrl;
    private Bitmap currentBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        ivPreview = findViewById(R.id.ivPreview);
        btnSetWallpaper = findViewById(R.id.btnSetWallpaper);

        imageUrl = getIntent().getStringExtra("wallpaper_url");

        // Load image (simplified - use Glide/Picasso in production)
        new Thread(() -> {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                currentBitmap = BitmapFactory.decodeStream(connection.getInputStream());
                runOnUiThread(() -> ivPreview.setImageBitmap(currentBitmap));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        btnSetWallpaper.setOnClickListener(v -> setWallpaper());
    }

    private void setWallpaper() {
        if (currentBitmap != null) {
            try {
                WallpaperManager wm = WallpaperManager.getInstance(this);
                wm.setBitmap(currentBitmap);
                Toast.makeText(this, "✅ Wallpaper set!", Toast.LENGTH_SHORT).show();
                finish();
            } catch (IOException e) {
                Toast.makeText(this, "❌ Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}