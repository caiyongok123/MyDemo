package com.example.cy.myapplication.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 蔡勇 on 2017-10-21.
 */

public class MyView  extends View {

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#55550000"));

        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        p.setStrokeWidth(50);

        canvas.drawLine(0,0,200,200,p);
    }
}