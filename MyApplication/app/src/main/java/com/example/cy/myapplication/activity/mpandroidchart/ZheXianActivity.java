package com.example.cy.myapplication.activity.mpandroidchart;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cy.myapplication.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

/**
 * 折线图
 */
public class ZheXianActivity extends AppCompatActivity {

    LineChart mLineChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhe_xian);
        mLineChar = (LineChart) findViewById(R.id.chart);

        //设置描述
        Description description = new Description();
        description.setText("正式表格的描述");//描述的文字
        description.setTypeface(Typeface.DEFAULT_BOLD);//描述文字的字体

        mLineChar.setDescription(description);

        //x轴
        LimitLine llXAxis = new LimitLine(10f, "标记");
        //设置线宽
        llXAxis.setLineWidth(4f);
        //
        llXAxis.enableDashedLine(10f, 10f, 0f);
        //设置
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = mLineChar.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        //设置x轴的最大值
        xAxis.setAxisMaximum(100f);
        //设置x轴的最小值
        xAxis.setAxisMinimum(0f);

        LimitLine ll1 = new LimitLine(150f, "优秀");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine(30f, "不及格");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        YAxis leftAxis = mLineChar.getAxisLeft();
        //重置所有限制线,以避免重叠线
        leftAxis.removeAllLimitLines();
        //设置优秀线
        leftAxis.addLimitLine(ll1);
        //设置及格线
        leftAxis.addLimitLine(ll2);
        //y轴最大
        leftAxis.setAxisMaximum(200f);
        //y轴最小
        leftAxis.setAxisMinimum(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // 限制数据(而不是背后的线条勾勒出了上面)
        leftAxis.setDrawLimitLinesBehindData(true);

        mLineChar.getAxisRight().setEnabled(false);

        //这里我模拟一些数据
        ArrayList<Entry> values = new ArrayList<Entry>();
        values.add(new Entry(5, 50));
        values.add(new Entry(10, 66));
        values.add(new Entry(15, 120));
        values.add(new Entry(20, 30));
        values.add(new Entry(35, 10));
        values.add(new Entry(40, 110));
        values.add(new Entry(45, 30));
        values.add(new Entry(50, 160));
        values.add(new Entry(100, 30));

        //设置数据
        setData(values);

        //默认动画
        mLineChar.animateX(2500);
        //刷新
        //mChart.invalidate();

        // 得到这个文字
        Legend l = mLineChar.getLegend();

        // 修改文字 ...
        l.setForm(Legend.LegendForm.LINE);
    }

    LineDataSet set1;

    //传递数据集
    private void setData(ArrayList<Entry> values) {
        if (mLineChar.getData() != null && mLineChar.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mLineChar.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mLineChar.getData().notifyDataChanged();
            mLineChar.notifyDataSetChanged();
        } else {
            // 创建一个数据集,并给它一个类型
            set1 = new LineDataSet(values, "年度总结报告");

            // 在这里设置线
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // 填充背景只支持18以上
                //Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.ic_launcher);
                //set1.setFillDrawable(drawable);
                set1.setFillColor(Color.YELLOW);
            } else {
                set1.setFillColor(Color.BLACK);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            //添加数据集
            dataSets.add(set1);

            //创建一个数据集的数据对象
            LineData data = new LineData(dataSets);

            //谁知数据
            mLineChar.setData(data);
        }
    }
}
