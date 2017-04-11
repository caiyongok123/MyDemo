package com.example.cy.myapplication.widget.mycalendar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cy.myapplication.R;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by joson on 2017/4/10.
 */
public class MyCalendarView extends LinearLayout implements ViewPager.OnPageChangeListener {
    Context context;
    View view;
    @Bind(R.id.tv_yymm)
    TextView tvYYMM;
    @Bind(R.id.vp)
    ViewPager vp;
    MyAdapter adapter;

    Date selectDay = null;
    int index = (new Date().getYear() - 70) * 12 + new Date().getMonth();


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

        vp.addOnPageChangeListener(this);
        adapter = new MyAdapter();
        vp.setAdapter(adapter);
        vp.setCurrentItem(index);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Date date = new Date(0);
        date.setYear(position == 0 ? 70 : 70 + position / 12);
        date.setMonth(position % 12);
        tvYYMM.setText(DateFormat.format("yyyy/MM",date));
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class MyAdapter extends PagerAdapter {

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
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_calendar_grid, null);

            container.addView(itemView);
            MyCalendarGridView myCalendarGridView = (MyCalendarGridView) itemView.findViewById(R.id.mcgv);
            Date date = new Date(0);
            date.setYear(position == 0 ? 70 : 70 + position / 12);
            date.setMonth(position % 12);
            myCalendarGridView.setFirstDay(date);
            Log.e("xxxxxxxxxxxxxxxx", date.toLocaleString());

            return itemView;
        }
    }



}
