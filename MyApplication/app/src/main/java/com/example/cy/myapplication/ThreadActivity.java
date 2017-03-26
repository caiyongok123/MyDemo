package com.example.cy.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ThreadActivity extends AppCompatActivity {


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
        try {
            timer.cancel();
        }catch (Exception e){}
    }
}
