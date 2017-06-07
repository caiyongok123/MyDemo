package com.example.cy.myapplication.activity.mpandroidchart;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.cy.myapplication.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;

/**
 * MPAndroidChart绘制曲线图，柱状图等
 */
public class MPAndroidChartActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpandroid_chart);
    }

    //折线图
    public void zheXianTu(View view){
        startActivity(new Intent(this,ZheXianActivity.class));
    }
}
