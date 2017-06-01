package com.example.cy.myapplication.activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cy.myapplication.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;

/**
 * MPAndroidChart绘制曲线图，柱状图等
 */
public class MPAndroidChartActivity extends AppCompatActivity {

    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpandroid_chart);
        lineChart = (LineChart) findViewById(R.id.chart);


        //设置描述
        Description description = new Description();
        description.setText("正式表格的描述");//描述的文字
        description.setTypeface(Typeface.DEFAULT_BOLD);//描述文字的字体

        lineChart.setDescription(description);

        //lineChart.setDrawYValues

    }
}
