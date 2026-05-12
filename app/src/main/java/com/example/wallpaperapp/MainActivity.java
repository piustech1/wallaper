package com.example.wallpaperapp;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {
    private ActivityResultLauncher<String> imagePicker;
    private WallpaperManager wallpaperManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wallpaperManager = WallpaperManager.getInstance(this);
        imagePicker = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> { if (uri != null) setWallpaper(uri); }
        );
        findViewById(R.id.btnPick).setOnClickListener(v -> imagePicker.launch("image/*"));
    }

    private void setWallpaper(Uri uri) {
        try (InputStream input = getContentResolver().openInputStream(uri)) {
            Bitmap original = BitmapFactory.decodeStream(input);
            if (original == null) {
                Toast.makeText(this, "Failed to decode image", Toast.LENGTH_SHORT).show();
                return;
            }
            int targetW = wallpaperManager.getDesiredMinimumWidth();
            int targetH = wallpaperManager.getDesiredMinimumHeight();
            Bitmap scaled = Bitmap.createScaledBitmap(original, targetW, targetH, true);
            wallpaperManager.setBitmap(scaled, null, true, WallpaperManager.FLAG_SYSTEM);
            Toast.makeText(this, "✅ Wallpaper set successfully!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "❌ Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}