package com.example.cy.myapplication.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.cy.myapplication.R;

public class BarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //透明状态栏
        setTranslucentStatusBar();
        // 全屏和状态栏设置为浅色模式(去除半透明色，文字为黑色（默认为半透明色，文字为白色）)
        setFullScreenAndLightStatusBar();

        setContentView(R.layout.activity_bar);
        setFitsSystemWindows();


    }


    /**
     * 设置半透明状态栏
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setTranslucentStatusBar(){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    /**
     * 设置状态栏颜色为全透明21以上
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setTransparentStatusBar(){
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    /**
     * *********这个方法在有些手机上无效********
     * 设置全屏 和 设置状态栏为浅色模式(去除半透明色，文字为黑色（默认为半透明色，文字为白色）)
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void setFullScreenAndLightStatusBar(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }


    /**
     * 使布局不会顶到屏幕顶部(不与状态栏重叠)，(必须在setContentView 方法执行后才生效)
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void setFitsSystemWindows(){
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        parentView.setFitsSystemWindows(true);
    }
}
