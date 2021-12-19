package com.example.bokeh;

import android.app.WallpaperManager;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(v-> { //ВЫБОР ОБОЕВ ПРИ НАЖАТИИ НА КНОПКУ
            Intent intent = new Intent();
            intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
            startActivity(intent);
        });
    }
}