package com.example.cy.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *
 * 获取电源锁
 * 电源设置参数：
 * PARTIAL_WAKE_LOCK:保持CPU 运转，屏幕和键盘灯有可能是关闭的。
 * SCREEN_DIM_WAKE_LOCK：保持CPU 运转，允许保持屏幕显示但有可能是灰的，允许关闭键盘灯
 * SCREEN_BRIGHT_WAKE_LOCK：保持CPU 运转，允许保持屏幕高亮显示，允许关闭键盘灯
 * FULL_WAKE_LOCK：保持CPU 运转，保持屏幕高亮显示，键盘灯也保持亮度
 * ACQUIRE_CAUSES_WAKEUP：强制使屏幕亮起，这种锁主要针对一些必须通知用户的操作.
 * ON_AFTER_RELEASE：当锁被释放时，保持屏幕亮起一段时间
 */
public class ThreadActivity extends AppCompatActivity {

    PowerManager.WakeLock wakeLock;

    @InjectView(R.id.tv)
    TextView tv;
    int time = 0;

    Timer timer = new Timer();
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv.setText(time+"");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        ButterKnife.inject(this);

        //电源管理设置让后台线程不会休眠
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, this.getClass().getCanonicalName());
        wakeLock.acquire();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                time++;
                handler.sendEmptyMessage(1);
            }
        },0,1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭电源设置
        if (wakeLock !=null&& wakeLock.isHeld()) {
            wakeLock.release();
            wakeLock =null;
        }
        try {
            timer.cancel();
        }catch (Exception e){}
    }
}
