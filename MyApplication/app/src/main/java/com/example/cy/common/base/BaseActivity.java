package com.example.cy.common.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.widget.view.TitleBar;
import com.lzy.okgo.OkGo;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by dell on 2017/11/20.
 */

public class BaseActivity extends AppCompatActivity implements TitleBar.OnTitleBarClickListener {

    public Context mContext;
    public BaseActivity mActivity;
    public FragmentManager mFragmentManager;
    public LoadingDialog dialog;

    public TitleBar mTitleBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("UiClassName:",getClass().getSimpleName());
        mContext = getApplicationContext();
        mActivity = this;
        mFragmentManager = getSupportFragmentManager();

        //设置为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //沉浸式
        adaptTheme(true);
        StatusBarLightMode(this);

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setFitsSystemWindows(true);
    }

    public void setContentView(int layoutResID,boolean isFitsSystemWindows) {
        super.setContentView(layoutResID);
        setFitsSystemWindows(isFitsSystemWindows);
    }

    /**
     * 设置布局内容和状态栏不重合
     * @param fitsSystemWindows
     */
    public void setFitsSystemWindows(boolean fitsSystemWindows){
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);

        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(fitsSystemWindows);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTitleBar = (TitleBar) findViewById(R.id.titleBar);
        if (mTitleBar!=null){
            mTitleBar.setOnTitleBarClickListener(this);
        }
    }


    boolean isShowing = false;


    /**
     * 透明状态栏
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void adaptTheme(final boolean isTranslucentStatusFitSystemWindowTrue) {
        if (isTranslucentStatusFitSystemWindowTrue) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }


    /**
     * 设置状态栏黑色字体图标，
     * 6.0以上版本Android
     */
    public void StatusBarLightMode(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    /**
     * 显示弹窗
     */
    public void showProgressDialog(){showProgressDialog(true);}
    public void showProgressDialog(boolean cancelable) {
        try {
            if (dialog == null) {
                dialog = new LoadingDialog(this,mFragmentManager);
                dialog.setCancelable(cancelable);
                if (!isFinishing() && !isShowing) {
                    isShowing = true;
                    dialog.show();
                }
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.cancel();
                        onProgressDialogCancelByUser();
                    }
                });
            } else if (!isShowing && !isFinishing()) {
                if (!dialog.isAdded()) {
                    isShowing = true;
                    dialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 隐藏弹窗
     */
    public void dismissProgressDialog() {
        try {
            if (dialog != null && isShowing) {
                isShowing = false;
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 当用户按返回键取消了等待框
     */
    public void onProgressDialogCancelByUser(){
        OkGo.getInstance().cancelTag(this);
    }



    @Override
    public void onBackClick() {//标题栏返回键被点击
        onBackPressed();
    }

    @Override
    public void onRightClick() {//标题栏右边的文字或者图标被点击

    }
}
