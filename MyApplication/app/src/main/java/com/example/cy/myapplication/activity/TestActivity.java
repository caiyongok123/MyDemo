package com.example.cy.myapplication.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.example.cy.myapplication.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
    }

    int i = 0;
    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(TestActivity.this);
        Notification notification = builder
                .setSmallIcon(R.drawable.icon_sel)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.iv_directory))
                .setContentTitle("11111111111")
                .setContentText("2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222")

                .build();

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(i++,notification);
    }
}
