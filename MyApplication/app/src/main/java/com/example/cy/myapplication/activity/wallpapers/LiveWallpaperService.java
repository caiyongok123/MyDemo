package com.example.cy.myapplication.activity.wallpapers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.Date;

/**
 * Created by joson on 2017/7/27.
 */

public class LiveWallpaperService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        Log.e("xxxxxxxxxxxxxxxxxxxx","YYYYYYYYYYYYYYYYY2222222");
        return new MyEngine();
    }


    Canvas canvas;
    Handler handler;
    Paint paint;

    public class MyEngine extends Engine {

        @Override
        public void onCreate(final SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            Log.e("xxxxxxxxxxxxxxxxxxxx","YYYYYYYYYYYYYYYYY");
            paint = new Paint();
            paint.setColor(Color.RED);
            canvas = surfaceHolder.lockCanvas();
            canvas.drawText(new Date().toLocaleString(), 100, 100, new Paint());
            canvas.drawLine(0,0,300,300,paint);
            //surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }
}
