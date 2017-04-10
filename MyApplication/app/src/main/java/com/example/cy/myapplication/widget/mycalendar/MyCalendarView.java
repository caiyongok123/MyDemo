package com.example.cy.myapplication.widget.mycalendar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.cy.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by joson on 2017/4/10.
 */
public class MyCalendarView extends LinearLayout {
    Context context;
    View view;
    @Bind(R.id.vp)
    ViewPager vp;
    MyAdapter adapter;

    public MyCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public MyCalendarView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    void initView() {
        view = LayoutInflater.from(context).inflate(R.layout.view_my_calendar, this);
        ButterKnife.bind(view);
        adapter = new MyAdapter();
        vp.setAdapter(adapter);

    }


    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_calendar_grid,null);
            MyCalendarGridView myCalendarGridView = (MyCalendarGridView) view.findViewById(R.id.mcgv);
            return view;
        }
    }
}
