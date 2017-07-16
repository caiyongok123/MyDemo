package com.example.cy.myapplication.widget.view.customloading;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.example.cy.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cy on 2017/7/16.
 * 多个小球绕圈的动画，像华为的等待框那样
 */

public class BallsRoundingView extends RelativeLayout {


    int minTime = 1000, tempTime = 80;//最快的小球一圈的时间，小球之间一圈时间的差值

    List<View> balls = new ArrayList();


    View view;

    public class Ball {
        public int duration;
        public int colorId;
        public int radiusDp;

        int x, y;

        public Ball(int colorId, int radiusDp) {
            this.colorId = colorId;
            this.radiusDp = radiusDp;
        }
    }


    public BallsRoundingView(Context context) {
        super(context, null);
    }

    public BallsRoundingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.balls, this);


    }


    int width, height;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        width = r - l;
        height = b - t;
        Log.e("宽高", width + "  " + height);
        addBalls();
        play();
    }

    Handler han = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            play();
        }
    };


    private void play() {
        for (int i = 0; i < balls.size(); i++) {
            ObjectAnimator oa = (ObjectAnimator) balls.get(i).getTag();
            oa.start();
        }
    }

    /**
     * 添加小球,跑得快的小球先添加
     */
    void addBalls() {
        balls.add(view.findViewById(R.id.ball1));
        balls.add(view.findViewById(R.id.ball2));
        balls.add(view.findViewById(R.id.ball3));
        for (int i = 0; i < balls.size(); i++) {
            View ball = balls.get(i);
            //ball.setPivotX(width / 2 - dp2Px(20));
            ball.setPivotY(height / 2);
            ObjectAnimator oa = ObjectAnimator.ofFloat(ball, "rotation", 0, 360);
            oa.setDuration(minTime + (i * tempTime));//设置动画时间
            oa.setInterpolator(new AccelerateDecelerateInterpolator());//设置动画插入器，减速
            oa.setRepeatCount(1);//设置动画重复次数，这里-1代表无限

            if (i==balls.size()-1){
                oa.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        han.sendEmptyMessage(1);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }

            ball.setTag(oa);
        }
    }


    int dp2Px(int dp) {
        return (int) (getContext().getResources().getDisplayMetrics().density * dp);
    }
}
