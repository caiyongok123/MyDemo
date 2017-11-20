package com.example.cy.common.base;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.cy.myapplication.R;

/**
 * Created by cy on 2016/7/27.
 * 等待框
 */
@SuppressLint("ValidFragment")
public class LoadingDialog extends DialogFragment {

    View view;
    Context mContext;
    DialogInterface.OnCancelListener onCancelListener;
    public FragmentManager mFragmentManager;

    public LoadingDialog(Context context,FragmentManager fragmentManager) {
        this.mContext=context;
        this.mFragmentManager = fragmentManager;
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener){
        this.onCancelListener=onCancelListener;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (onCancelListener!=null)
            onCancelListener.onCancel(dialog);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
        getDialog().getWindow().setGravity(Gravity.CENTER);

        getDialog().setCanceledOnTouchOutside(false);

        view=inflater.inflate(R.layout.dialog_loading,container);

        return view;
    }

    public boolean isShowing(){
        if (getDialog()!=null  && getDialog().isShowing())
            return true;
        return false;
    }

    public void show() {
        try {
            show(mFragmentManager,"LoadingDialog");
        }catch (Exception e){
            show(mFragmentManager,"LoadingDialog");

        }

    }
}
