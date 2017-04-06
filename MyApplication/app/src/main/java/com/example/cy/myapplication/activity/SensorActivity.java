package com.example.cy.myapplication.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cy.myapplication.R;

/**
 * 手机旋转监听
 */
public class SensorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        setRotateSensorListener(this);
    }

    private void setRotateSensorListener(Context context) {
        context.getSystemService(Context.SENSOR_SERVICE);
    }
}
