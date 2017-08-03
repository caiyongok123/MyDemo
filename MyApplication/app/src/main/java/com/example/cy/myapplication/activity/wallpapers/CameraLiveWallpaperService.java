package com.example.cy.myapplication.activity.wallpapers;


import android.hardware.Camera;
import android.media.MediaPlayer;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import java.io.IOException;


public class CameraLiveWallpaperService extends WallpaperService {


    @Override
    public Engine onCreateEngine() {
        return new MyEngine();
    }

    Camera mCamera;
    SurfaceHolder mHolder;


    class MyEngine extends Engine {


        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            mHolder = holder;
            holder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        if (mCamera == null) {
                            mCamera = Camera.open();
                            mCamera.setDisplayOrientation(90);
                        }
                        mCamera.setPreviewDisplay(holder);
                        mCamera.startPreview();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {

                }
            });
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (visible) {
                if (mCamera == null) {
                    mCamera = Camera.open();
                    mCamera.setDisplayOrientation(90);
                }
                try {
                    mCamera.setPreviewDisplay(mHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mCamera.startPreview();
            } else {
                try {
                    mCamera.stopPreview();
                } catch (Exception e) {
                }

            }
        }
    }
}