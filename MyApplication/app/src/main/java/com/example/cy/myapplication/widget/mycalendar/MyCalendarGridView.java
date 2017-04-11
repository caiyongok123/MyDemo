package com.example.cy.myapplication.widget.mycalendar;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cy.myapplication.R;

import java.util.ArrayList;
import java.util.Date;

import static android.view.Gravity.CENTER;

/**
 * Created by joson on 2017/4/10.
 */
public class MyCalendarGridView extends RelativeLayout {

    Context context;
    Date firstDay;//这个月的第一天
    View view;

    GridView gv;
    BaseAdapter adapter;

    //6行显示的所有日期
    ArrayList<Date> dates = new ArrayList();



    public void setFirstDay(Date firstDay){
        this.firstDay = firstDay;

        initDates();
        Log.e("xxxxxx","xxxxxxxxxxxxxxxxxxxxx");
        adapter.notifyDataSetChanged();
    }


    public MyCalendarGridView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public MyCalendarGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    void initView() {
        view = LayoutInflater.from(context).inflate(R.layout.view_calendar, this);
        gv = (GridView) view.findViewById(R.id.gv);


        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return 6*7;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Date date = dates.get(position);
                View itemView = LayoutInflater.from(context).inflate(R.layout.item_text,null);
                TextView tv = (TextView) itemView.findViewById(R.id.tv_item);
                tv.setText(date.getDate()+"");
                tv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,view.getWidth()/7));
                tv.setGravity(CENTER);
                if (date.getMonth() == firstDay.getMonth()){
                    tv.setTextColor(Color.parseColor("#000000"));
                }else {
                    tv.setTextColor(Color.parseColor("#999999"));
                }
                return itemView;
            }
        };

        gv.setAdapter(adapter);
    }

    private void initDates() {
        if (firstDay.getDay()>0){//前面的补全
            for (int i = firstDay.getDay();i>0;i--){
                dates.add(new Date(firstDay.getTime()-i*24*60*60*1000));
            }
        }
        Date temp = new Date(firstDay.getTime());
        for (int i = 7*6-dates.size(); i>0;i--){//填满6行
            dates.add(temp);
            temp=new Date(temp.getTime()+24*60*60*1000);
        }
    }
}
