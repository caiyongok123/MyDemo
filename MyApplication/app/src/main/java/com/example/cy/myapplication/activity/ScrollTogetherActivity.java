package com.example.cy.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.widget.view.MyScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScrollTogetherActivity extends AppCompatActivity {

    @Bind(R.id.sv1)
    MyScrollView sv1;
    @Bind(R.id.sv2)
    MyScrollView sv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srcoll_together);
        ButterKnife.bind(this);

        sv1.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {

            }
        });

        sv2.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {

            }
        });
    }
}
