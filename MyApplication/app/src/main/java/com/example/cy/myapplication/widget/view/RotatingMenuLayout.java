package com.example.cy.myapplication.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.cy.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蔡勇 on 2017-9-20.
 * 旋转菜单布局
 */

public class RotatingMenuLayout extends ViewGroup {

    Context context;

    int itemLayoutId;
    List<Object> itemList;
    List<View> itemViewList;

    int resWidth = 0;
    int resHeight = 0;

    int itemSize;

    int angle = 0; //偏移角度

    public RotatingMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setPadding(0, 0, 0, 0);
        /*setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        angle += 1;
                        itemsLayout();
                        handler.postDelayed(this, 1);
                    }
                }, 100);
            }
        });*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setTextSize(260);


        p.setColor(Color.BLUE);
        p.setStyle(Paint.Style.FILL);
        p.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText("ds地方", 100, 300, p);

        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);

        canvas.drawText("ds地方", 100, 300, p);

    }

    public void setItemList(List<Object> list) {
        removeAllViews();
        itemList = list;
        itemViewList = new ArrayList<>();
        if (itemList != null) {
            for (int i = 0; i < itemList.size(); i++) {
                if (itemLayoutId == 0) {
                    itemLayoutId = R.layout.item_rotating_menu;
                }
                itemViewList.add(LayoutInflater.from(context).inflate(itemLayoutId, null));
                addView(itemViewList.get(i));
            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 对于长宽，如果xml中设置的是具体值，那就长宽统一为短的那个，如果不是具体值，就充满父布局（保持正方形）
         */


        /**
         * 根据传入的参数，分别获取测量模式和测量值
         */
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        /**
         * 如果宽或者高的测量模式非精确值
         */
        if (widthMode != MeasureSpec.EXACTLY
                || heightMode != MeasureSpec.EXACTLY) {
            // 主要设置为背景图的高度
            resWidth = getSuggestedMinimumWidth();
            // 如果未设置背景图片，则设置为屏幕宽高的默认值
            resWidth = resWidth == 0 ? getDefaultWidth() : resWidth;

            resHeight = getSuggestedMinimumHeight();
            // 如果未设置背景图片，则设置为屏幕宽高的默认值
            resHeight = resHeight == 0 ? getDefaultWidth() : resHeight;
        } else {
            // 如果都设置为精确值，则直接取小值；
            resWidth = resHeight = Math.min(width, height);
        }

        setMeasuredDimension(resWidth, resHeight);

        for (int index = 0; index < getChildCount(); index++) {
            final View child = getChildAt(index);
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("xxxcy", "l=" + l + ",t=" + t + ",r=" + r + ",b=" + b);
        itemsLayout();
    }

    void itemsLayout() {
        if (itemList != null && !itemList.isEmpty()) {
            for (int i = 0; i < itemList.size(); i++) {
                View view = getChildAt(i);
                int[] xy = getXY(i);
                if (xy != null) {
                    view.layout(xy[0], xy[1], xy[0] + itemSize, xy[1] + itemSize);
                }

            }
        }
    }


    /**
     * 获得默认该layout的尺寸(父布局的长宽取其短)
     *
     * @return
     */
    private int getDefaultWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }


    int[] getXY(int index) {
        if (itemList != null && !itemList.isEmpty()) {
            int x, y;
            x = y = resWidth / 2;
            itemSize = itemViewList.get(0).getMeasuredWidth();
            int radius = (int) Math.sqrt(x * x - (itemSize / 2) * (itemSize / 2)) - itemSize / 2;
            float f = (360f / ((float) itemList.size()) * index + angle) % 360;
            if (f >= 0 && f < 90) {
                x = (int) (x + Math.sin(f / 180f * Math.PI) * radius);
                y = (int) (y - Math.cos(f / 180f * Math.PI) * radius);
            } else if (f >= 90 && f < 180) {
                f -= 90;
                x = (int) (x + Math.cos(f / 180f * Math.PI) * radius);
                y = (int) (y + Math.sin(f / 180f * Math.PI) * radius);
            } else if (f >= 180 && f < 270) {
                f -= 180;
                x = (int) (x - Math.sin(f / 180f * Math.PI) * radius);
                y = (int) (y + Math.cos(f / 180f * Math.PI) * radius);
            } else if (f >= 270 && f < 360) {
                f -= 270;
                x = (int) (x - Math.cos(f / 180f * Math.PI) * radius);
                y = (int) (y - Math.sin(f / 180f * Math.PI) * radius);
            }
            return new int[]{x - itemSize / 2, y - itemSize / 2};
        } else {
            return null;
        }
    }


    float touchX, touchY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("xxxcy",event.getAction()+"========="+event.getX()+"==========="+event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float tempX = event.getX();
                float tempY = event.getY();
                float tempAngle = getAngle(new Point(resWidth/2,resWidth/2),new Point((int)touchX,(int)touchY),new Point((int)tempX,(int)tempY));;
                if (tempAngle>0){
                    angle+= tempAngle;
                    Log.e("xxxcy",""+angle);
                    touchX = tempX;
                    touchY = tempY;
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;

    }


    public static float getAngle(Point cen, Point first, Point second) {
        float dx1, dx2, dy1, dy2;
        float angle;

        dx1 = first.x - cen.x;
        dy1 = first.y - cen.y;

        dx2 = second.x - cen.x;

        dy2 = second.y - cen.y;

        float c = (float) Math.sqrt(dx1 * dx1 + dy1 * dy1) * (float) Math.sqrt(dx2 * dx2 + dy2 * dy2);

        if (c == 0) return -1;

        angle = (float) Math.acos((dx1 * dx2 + dy1 * dy2) / c);

        return angle;
    }
}
