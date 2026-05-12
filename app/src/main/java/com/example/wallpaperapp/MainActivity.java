package com.example.wallpaperapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.activity.ComponentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends ComponentActivity {

    // Sample wallpaper URLs (replace with your own)
    private List<String> wallpaperUrls = Arrays.asList(
        "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
        "https://images.unsplash.com/photo-1469474968028-56623f02e42e?w=800",
        "https://images.unsplash.com/photo-1447752875215-b2761acb3c5d?w=800",
        "https://images.unsplash.com/photo-1433086966358-54859d0ed716?w=800",
        "https://images.unsplash.com/photo-1501785888041-af3ef285b470?w=800",
        "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?w=800"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new WallpaperAdapter());
    }

    class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wallpaper, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(wallpaperUrls.get(position));
        }

        @Override
        public int getItemCount() {
            return wallpaperUrls.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            ViewHolder(View view) {
                super(view);
                imageView = view.findViewById(R.id.ivWallpaper);
            }
            void bind(String url) {
                // Simple placeholder - in real app use Glide/Picasso
                imageView.setImageResource(android.R.drawable.ic_menu_gallery);
                imageView.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, PreviewActivity.class);
                    intent.putExtra("wallpaper_url", url);
                    startActivity(intent);
                });
            }
        }
    }
}