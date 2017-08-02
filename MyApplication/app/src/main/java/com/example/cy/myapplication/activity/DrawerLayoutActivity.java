package com.example.cy.myapplication.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.example.cy.myapplication.R;

import java.lang.reflect.Field;

public class DrawerLayoutActivity extends AppCompatActivity {

    DrawerLayout dl;

    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);

        dl = (DrawerLayout) findViewById(R.id.dl);
        /*dl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setDraweLayout(dl);
            }
        });*/
        ll = (LinearLayout) findViewById(R.id.ll);
        ll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setDraweLayout(dl,ll);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * 设置抽屉布局响应抽屉拖出的范围为隐藏布局的宽度
     *
     * @param drawerLayout
     */
    void setDraweLayout(DrawerLayout drawerLayout, View menuView) {
        Field mDragger = null;
        try {
            mDragger = drawerLayout.getClass().getDeclaredField(
                    "mLeftDragger"); //mRightDragger for right obviously
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mDragger.setAccessible(true);
        ViewDragHelper draggerObj = null;
        try {
            draggerObj = (ViewDragHelper) mDragger
                    .get(drawerLayout);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Field mEdgeSize = null;
        try {
            mEdgeSize = draggerObj.getClass().getDeclaredField(
                    "mEdgeSize");
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mEdgeSize.setAccessible(true);
        int edge = 0;
        try {
            edge = mEdgeSize.getInt(draggerObj);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            mEdgeSize.setInt(draggerObj, menuView.getWidth()); //edge * 5); //optimal value as for me, you may set any constant in dp
            //You can set it even to the value you want like mEdgeSize.setInt(draggerObj, 150); for 150dp
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
