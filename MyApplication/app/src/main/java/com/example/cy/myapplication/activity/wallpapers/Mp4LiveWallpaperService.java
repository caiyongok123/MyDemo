package com.example.cy.myapplication.activity.wallpapers;


import android.media.MediaPlayer;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;


public class Mp4LiveWallpaperService extends WallpaperService {

    public static String mp4Path;

    MediaPlayer mediaPlayer;

    @Override
    public Engine onCreateEngine() {
        return new MyEngine();
    }

    class MyEngine extends Engine {

        @Override
        public void onSurfaceCreated(final SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setSurface(holder.getSurface());
            try {
                mediaPlayer.setDataSource(Mp4LiveWallpaperService.this.mp4Path);
                mediaPlayer.setLooping(true);
                //静音
                mediaPlayer.setVolume(0, 0);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.e("live", "onSurfaceCreated");

        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (visible){
                mediaPlayer.start();
            }else {
                mediaPlayer.pause();
            }
        }
    }
}
