package com.example.cy.myapplication.activity;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.util.RotateSensorListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 手机旋转监听
 */
public class SensorActivity extends AppCompatActivity {

    @Bind(R.id.tv_qianfan)
    TextView tvQianfan;
    @Bind(R.id.tv_houfan)
    TextView tvHoufan;
    @Bind(R.id.tv_zuofan)
    TextView tvZuofan;
    @Bind(R.id.tv_youfan)
    TextView tvYoufan;
    @Bind(R.id.tv_shunfan)
    TextView tvShunfan;
    @Bind(R.id.tv_nifan)
    TextView tvNifan;


    //手机转圈传感器监听
    RotateSensorListener rotateSensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        ButterKnife.bind(this);
        rotateSensorListener = new RotateSensorListener(this) {
            @Override
            public void onQianfan(int num) {
                tvQianfan.setText(num+"");
            }

            @Override
            public void onHoufan(int num) {
                tvHoufan.setText(num+"");
            }
        };
        rotateSensorListener.register();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rotateSensorListener.unRegister();
    }
}
