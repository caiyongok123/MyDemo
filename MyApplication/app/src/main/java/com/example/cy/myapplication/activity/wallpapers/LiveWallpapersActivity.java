package com.example.cy.myapplication.activity.wallpapers;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import android.service.wallpaper.WallpaperService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;

import com.example.cy.myapplication.R;

import java.util.Date;

public class LiveWallpapersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_wallpapers);
        startService(new Intent(this,LiveWallpaperService.class));


    }




}
