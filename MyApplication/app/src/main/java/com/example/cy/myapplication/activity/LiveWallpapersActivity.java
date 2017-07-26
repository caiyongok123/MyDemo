package com.example.cy.myapplication.activity;

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
        startService(new Intent(this,LiveWallpapers.class));

    }

    public class LiveWallpapers extends WallpaperService{

        public LiveWallpapers() {

        }

        @Override
        public Engine onCreateEngine() {
            return new Engine();
        }


        Canvas canvas;
        class MyEngine extends Engine {

            public MyEngine() {
            }

            @Override
            public void onCreate(SurfaceHolder surfaceHolder) {
                super.onCreate(surfaceHolder);
                canvas = surfaceHolder.lockCanvas();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                canvas.drawText(new Date().toLocaleString(),100,100,new Paint());
                            }
                        });
                    }
                }).start();
            }
        }
    }




}
