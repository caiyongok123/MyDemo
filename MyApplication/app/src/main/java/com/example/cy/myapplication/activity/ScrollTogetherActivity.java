package com.example.cy.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cy.common.MyApplication;
import com.example.cy.myapplication.R;
import com.example.cy.myapplication.widget.view.MyScrollView;
import com.example.cy.myapplication.widget.view.customloading.MyHorizontalScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScrollTogetherActivity extends AppCompatActivity {


    @Bind(R.id.hsv_top)
    MyHorizontalScrollView hsvTop;
    @Bind(R.id.sv_left)
    MyScrollView svLeft;
    @Bind(R.id.hsv_center)
    MyHorizontalScrollView hsvCenter;
    @Bind(R.id.sv_center)
    MyScrollView svCenter;

    @Bind(R.id.ll_title_top)
    LinearLayout llTitleTop;
    @Bind(R.id.ll_title_left)
    LinearLayout llTitleLeft;
    @Bind(R.id.ll_center)
    LinearLayout llCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srcoll_together);
        ButterKnife.bind(this);

        setOnScroll();

        addItems();


    }

    private void addItems() {


        //上面的头
        for (int i = 'A'; i <= 'Z'; i++) {
            llTitleTop.addView(getItem((char) i + ""));
        }
        //左边的bar
        for (int i = 1; i <= 50; i++) {
            llTitleLeft.addView(getItem(i + ""));
        }

        //中间的项
        for (int j = 1; j <= 50; j++){
            LinearLayout ll = new LinearLayout(MyApplication.myApplication);
            ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            for (int i = 'A'; i <= 'Z'; i++) {
                ll.addView(getItem((char)i + ""+j));
            }
            llCenter.addView(ll);
        }


    }


    View getItem(String text) {
        View item = LayoutInflater.from(MyApplication.myApplication).inflate(R.layout.item_text_50_50, null);
        ((TextView)item.findViewById(R.id.tv)).setText(text + "");
        return item;
    }


    /**
     * 设置联动
     */
    private void setOnScroll() {
        //横向联动
        hsvTop.setOnScrollListener(new MyHorizontalScrollView.OnScrollListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                hsvCenter.scrollTo(l, t);
            }
        });
        hsvCenter.setOnScrollListener(new MyHorizontalScrollView.OnScrollListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                hsvTop.scrollTo(l, t);
            }
        });

        //纵向联动
        svLeft.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                svCenter.scrollTo(l, t);
            }
        });

        svCenter.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                svLeft.scrollTo(l, t);
            }
        });
    }
}
