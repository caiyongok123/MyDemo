package com.example.cy.myapplication.widget.mycalendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cy.myapplication.R;

/**
 * Created by joson on 2017/4/10.
 */
public class MyCalendarGridView extends RelativeLayout {

    Context context;
    View view;
    GridView gv;
    BaseAdapter adapter;

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
        View view = LayoutInflater.from(context).inflate(R.layout.view_calendar, this);
        gv = (GridView) view.findViewById(R.id.gv);

        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return 50;
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
                TextView tv = new TextView(context);
                tv.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
                tv.setText("XX");
                return tv;
            }
        };

        gv.setAdapter(adapter);
    }
}
