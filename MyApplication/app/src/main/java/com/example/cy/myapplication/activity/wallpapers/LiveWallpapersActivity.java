package com.example.cy.myapplication.activity.wallpapers;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.VideoView;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.util.WallpaperUtil;

public class LiveWallpapersActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_wallpapers);

        WallpaperUtil.setLiveWallpaper(this,this,1);
    }





}
