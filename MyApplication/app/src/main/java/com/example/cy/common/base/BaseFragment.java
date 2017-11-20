package com.example.cy.common.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.okgo.OkGo;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * Created by dell on 2017/11/20.
 */

public abstract class BaseFragment extends Fragment{

    public Context mContext;
    public BaseActivity mActivity;
    boolean isShowing = false;
    public LoadingDialog dialog;
    public View rootView;


    public abstract int getLayoutResourceId();
    public abstract void init();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) getActivity();
        mContext = mActivity.getApplicationContext();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("UiClassName:",getClass().getSimpleName());
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResourceId(), container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        ButterKnife.bind(this,rootView);
        init();
        return rootView;
    }

    /**
     * 显示弹窗
     */
    public void showProgressDialog(){showProgressDialog(true);}
    public void showProgressDialog(boolean cancelable) {
        try {
            if (dialog == null) {
                dialog = new LoadingDialog(mActivity,mActivity.mFragmentManager);
                dialog.setCancelable(cancelable);
                if (!mActivity.isFinishing() && !isShowing) {
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
            } else if (!isShowing && !mActivity.isFinishing()) {
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

}
