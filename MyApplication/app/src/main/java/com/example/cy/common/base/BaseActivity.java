package com.example.cy.common.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.widget.view.TitleBar;
import com.lzy.okgo.OkGo;

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

        mContext = getApplicationContext();
        mActivity = this;
        mFragmentManager = getSupportFragmentManager();

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
